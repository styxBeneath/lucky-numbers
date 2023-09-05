package io.luckynumbers
package services

import cats.effect.IO

object ResultsCalculator {
  def calculateResults(tuple: (Int,Int)): (Int, Int, Int) = {
    (tuple._1, tuple._2, processNumber(tuple._2))
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
