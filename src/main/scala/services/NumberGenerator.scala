package io.luckynumbers
package services

import scala.util.Random

/**
 * NumberGenerator generates random numbers associated with players.
 */
object NumberGenerator {

  /**
   * Generates a random number for a player.
   *
   * @param player The player for whom to generate a number.
   * @return A tuple containing the player identifier and the generated random number.
   */
  def generateNumber(player: Int): (Int, Int) =
    (player, Random.nextInt(1000000))
}
