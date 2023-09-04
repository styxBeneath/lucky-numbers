package io.luckynumbers
package util

import cats.effect.IO

object Extensions {
  extension[A] (io: IO[A])
    def debug(text: String): IO[A] = io.map { value =>
      println(s"$text [$value]")
      value
    }
}
