package io.luckynumbers
package services

import cats.effect.IO
import messages.Result

object GameSimulator {
  def simulateGame(n: Int) : List[Result] = {
    val results = (0 to n).toList.map ( player => NumberGenerator.generateNumber(player))
      .map(ResultsCalculator.calculateResults)
    results.tail
      .filter(result => result._3 >= results.head._3)
      .sortBy(-_._3)
      .zipWithIndex
      .map { case ((player, number, result), position) =>
        Result(position + 1, player, number, result)
      }
  }
}
