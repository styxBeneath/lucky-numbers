package io.luckynumbers
package services

import cats.effect.IO

object ResultsCalculator {
  def calculateResults(n: Int): IO[List[(Int, Int, Int)]] = {
    val numberListIO: IO[List[(Int, Int)]] = NumberListGenerator.generateNumberList(n)

    numberListIO.flatMap { numberList =>
      IO {
        numberList.map { case (index, number) =>
          (index, number, processNumber(number))
        }
      }
    }
  }

  private def processNumber(n: Int): Int = {
    n.toString
      .groupBy(_.asDigit)
      .map { case (digit, s) => digit -> s.length } // digit -> n_occurrences
      .toList
      .map { case (digit, count) => math.pow(10, count - 1).toInt * digit }
      .sum
  }
}
