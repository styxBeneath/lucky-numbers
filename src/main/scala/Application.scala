package io.luckynumbers

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Flow
import services.{GameSimulator, NumberGenerator, ResultsCalculator}

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

/**
 * The main application object for the Lucky Number Game server.
 */
object Application extends IOApp {
  // Define execution context
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "LuckyNumberApplication")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  // Define the WebSocket route for handling WebSocket connections
  private val route =
    path("lucky-numbers") {
      handleWebSocketMessages(webSocketFlow)
    }

  /**
   * Defines the WebSocket flow for processing WebSocket messages.
   *
   * @return A flow that handles WebSocket messages.
   */
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
    }.mapAsync(200) { message => // Max number of messages processed in parallel
      println(s"Message received: $message")
      message match {
        case RequestPlay("request.play", players) =>
          // Simulate the game asynchronously
          IO(GameSimulator.simulateGame(players, None, None))
            .unsafeToFuture().map { result =>
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
    }.recover {
      case ex: Throwable =>
        ex.printStackTrace()
        TextMessage(Error("error", "WebSocket error").toJson.compactPrint)
    }
  }

  /**
   * Entry point for running the application.
   *
   * @param args The command-line arguments.
   * @return An `IO` representing the application's exit code.
   */
  override def run(args: List[String]): IO[ExitCode] = {
    // Create an HTTP server binding
    val bindingIO = IO.fromFuture(IO(Http().newServerAt("localhost", 8080).bind(route)))

    // Define the shutdown behavior
    val shutdownIO = IO {
      println("Application is running")
    } *> IO.never

    // Ensure proper resource management and shutdown
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
