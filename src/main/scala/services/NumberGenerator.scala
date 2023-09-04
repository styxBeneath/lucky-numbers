package io.luckynumbers
package services

import cats.effect.IO

import scala.util.Random

object NumberGenerator {
  def generateNumber: IO[Int] =
    IO(Random.nextInt(1000000))
}
