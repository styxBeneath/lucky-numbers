package io.luckynumbers
package util

import cats.effect.IO
import spray.json.enrichString
import scala.util.Try

/**
 * This object contains extension methods for enriching used types.
 */
object Extensions {

  /**
   * An extension method for IO that adds debugging capabilities.
   */
  extension[A] (io: IO[A]) {

    /**
     * Debugs the IO value by printing a message with the specified text.
     *
     * @param text The debug text to be printed.
     * @return An IO[A] value that includes debugging output.
     */
    def debug(text: String): IO[A] = io.map { value =>
      println(s"$text [$value]")
      value
    }
  }

  /**
   * An extension method for String that checks if it represents valid JSON.
   */
  extension (s: String) {

    /**
     * Checks if the String represents valid JSON.
     *
     * @return `true` if the String is valid JSON; otherwise, `false`.
     */
    def isJson: Boolean = {
      Try(s.parseJson).isSuccess
    }
  }
}