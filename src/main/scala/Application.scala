package io.luckynumbers

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Flow
import services.GameSimulator

import spray.json._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Try
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import messages._
import util.Extensions.isJson
import util.JSONUtil._
import cats.effect.ExitCode
import cats.effect.IOApp

object Application extends IOApp {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "LuckyNumberApplication")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  private val route =
    path("lucky-numbers") {
      handleWebSocketMessages(webSocketFlow)
    }

  private def webSocketFlow: Flow[Message, Message, Any] = {
    Flow[Message].collect {
      case TextMessage.Strict(jsonStr) if jsonStr.isJson =>
        val json = jsonStr.parseJson
        json match {
          case JsObject(fields) if fields.contains("message_type") =>
            fields("message_type") match {
              case JsString("request.play") =>
                json.convertTo[RequestPlay]
              case JsString("request.ping") =>
                json.convertTo[RequestPing]
              case _ =>
                Error("error", "Invalid message type")
            }
          case _ =>
            Error("error", "Invalid JSON structure")
        }
    }.mapAsync(1) { message =>
      println(s"Message received: $message")
      message match
        case RequestPlay("request.play", players) =>
          // Simulate the game asynchronously
          IO(GameSimulator.simulateGame(players)).unsafeToFuture().map { result =>
            val response = ResponseResults("response.results", result)
            TextMessage(response.toJson.compactPrint)
          }
        case RequestPing(id, "request.ping", timestamp) =>
          val responsePong = ResponsePong("response.pong", id, timestamp, System.currentTimeMillis())
          Future.successful(TextMessage(responsePong.toJson.compactPrint))
        case error: Error =>
          Future.successful(TextMessage(error.toJson.compactPrint))
        case _ =>
          Future.successful(TextMessage(Error("error", "Server error").toJson.compactPrint))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val bindingIO = IO.fromFuture(IO(Http().newServerAt("localhost", 8080).bind(route)))
    val shutdownIO = IO {
      println("Application is running")
    } *> IO.never

    bindingIO.bracket { binding =>
      shutdownIO
    } { binding =>
      IO {
        println("Shutting down Application...")
        binding.unbind().onComplete { _ =>
          system.terminate()
          println("Application terminated.")
        }
      }
    }
  }
}
