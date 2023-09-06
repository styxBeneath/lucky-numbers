package io.luckynumbers
package services

import cats.effect.IO
import messages.Result

/**
 * GameSimulator simulates a game involving multiple players and generates results.
 */
object GameSimulator {

  /**
   * Simulates a game with the specified number of players.
   *
   * @param n               The number of players in the game.
   * @param numGeneratorFn  An optional custom number generator function.
   *                        If provided, it will be used to generate player numbers.
   *                        If not provided, the default `NumberGenerator.generateNumber` function will be used.
   * @param resCalculatorFn An optional custom results calculator function.
   *                        If provided, it will be used to calculate results based on generated numbers.
   *                        If not provided, the default `ResultsCalculator.calculateResults` function will be used.
   * @return A list of results containing the position, player, generated number, and calculated result.
   */
  def simulateGame(n: Int, numGeneratorFn: Option[Int => (Int, Int)], resCalculatorFn: Option[((Int, Int)) => (Int, Int, Int)]): List[Result] = {
    // Determine the number generator and results calculator functions
    val generateNumber = numGeneratorFn.getOrElse(NumberGenerator.generateNumber)
    val calculateResults = resCalculatorFn.getOrElse(ResultsCalculator.calculateResults)

    // Simulate the game for the specified number of players. The player with index 0 is a bot
    val results: List[(Int, Int, Int)] = (0 to n).toList.map(generateNumber)
      .map(calculateResults)

    // Filter and sort the results to determine positions
    results.tail
      .filter(result => result._3 >= results.head._3)
      .sortBy(-_._3)
      .zipWithIndex
      .map { case ((player, number, result), position) =>
        Result(position + 1, player, number, result)
      }
  }
}