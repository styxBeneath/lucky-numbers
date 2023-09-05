package io.luckynumbers
package services

import scala.util.Random

object NumberGenerator {
  def generateNumber(player: Int): (Int, Int) =
    (player, Random.nextInt(1000000))
}
