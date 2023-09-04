package io.luckynumbers
package services

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import util.Result

object FinalResultCalculator {
  def calculateFinalResults(n: Int): IO[List[Result]] = {
    val resultIO: IO[List[(Int, Int, Int)]] = ResultsCalculator.calculateResults(n)

    resultIO.map { resultList =>
      val bot = resultList.head._3

      resultList.tail.map { case (player, number, result) =>
          val num: Int = number
          val res: Int = result
          println(s"num: $num  res: $res")
          (player, num, res)
        }
        .filter((_, _, res) => res >= bot)
        .sortBy(-_._3)
        .zipWithIndex
        .map { case ((player, num, res), index) =>
          Result(player, num, res, index + 1)
        }
    }
  }
}
