package io.luckynumbers
package util

import cats.effect.IO
import spray.json.enrichString
import scala.util.Try

object Extensions {
  extension[A] (io: IO[A])
    def debug(text: String): IO[A] = io.map { value =>
      println(s"$text [$value]")
      value
    }

  extension (s: String)
    def isJson: Boolean = {
      Try(s.parseJson).isSuccess
    }
}
