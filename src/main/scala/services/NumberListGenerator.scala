package io.luckynumbers
package services

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object NumberListGenerator {
  def generateNumberList(n: Int): IO[List[(Int, Int)]] = IO.delay {
    (0 to n).toList.map { index =>
      val number = NumberGenerator.generateNumber.unsafeRunSync()
      println(s"index: $index  number: $number")
      (index, number)
    }
  }
}
