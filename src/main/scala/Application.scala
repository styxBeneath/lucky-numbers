package io.luckynumbers

import services.FinalResultCalculator

import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.ExecutionContext
import scala.util.Random

object Application extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val n = 5

        for {
          result <- FinalResultCalculator.calculateFinalResults(n)
          _      <- IO(println(s"Results for $n: $result"))
        } yield ExitCode.Success

  }
}

