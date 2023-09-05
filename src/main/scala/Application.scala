package io.luckynumbers

import cats.effect.{ExitCode, IO, IOApp}
import services.GameSimulator

import util.JSONFormatter

import scala.concurrent.ExecutionContext
import scala.util.Random

object Application extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val n = 5

        for {
          result <- GameSimulator.simulateGame(n)
          _      <- IO(println(s"Results for $n: ${JSONFormatter.resultsToJson(result)}"))
        } yield ExitCode.Success

  }
}

