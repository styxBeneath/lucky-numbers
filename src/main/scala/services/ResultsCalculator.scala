package io.luckynumbers
package services

import cats.effect.IO

/**
 * ResultsCalculator calculates results based on a tuple of player and number.
 */
object ResultsCalculator {

  /**
   * Calculates results based on a tuple containing player and number.
   *
   * @param tuple A tuple containing the player identifier and the generated number.
   * @return A tuple containing the player identifier, the generated number, and the calculated result.
   */
  def calculateResults(tuple: (Int, Int)): (Int, Int, Int) = {
    (tuple._1, tuple._2, processNumber(tuple._2))
  }

  /**
   * Processes a number to calculate the result.
   *
   * @param n The number to be processed.
   * @return The calculated result based on the number.
   */
  private def processNumber(n: Int): Int = {
    n.toString
      .groupBy(_.asDigit)
      .map { case (digit, s) => digit -> s.length } // digit -> n_occurrences
      .toList
      .map { case (digit, count) => math.pow(10, count - 1).toInt * digit }
      .sum
  }
}

