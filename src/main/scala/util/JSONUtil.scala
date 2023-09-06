package io.luckynumbers
package util

import messages.{Error, RequestPing, RequestPlay, ResponsePong, ResponseResults, Result}
import spray.json._
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, listFormat, LongJsonFormat, jsonFormat2, jsonFormat3, jsonFormat4}

/**
 * This object provides utility methods for working with JSON serialization and deserialization.
 */
object JSONUtil {
  
  /**
   * Implicit JSON format for serializing and deserializing `Error` objects.
   */
  implicit val errorFormat: JsonFormat[Error] = jsonFormat2(Error.apply)

  /**
   * Implicit JSON format for serializing and deserializing `RequestPlay` objects.
   */
  implicit val requestPlayFormat: JsonFormat[RequestPlay] = jsonFormat2(RequestPlay.apply)

  /**
   * Implicit JSON format for serializing and deserializing `Result` objects.
   */
  implicit val resultFormat: JsonFormat[Result] = jsonFormat4(Result.apply)

  /**
   * Implicit JSON format for serializing and deserializing `ResponseResults` objects.
   */
  implicit val responseResultsFormat: JsonFormat[ResponseResults] = jsonFormat2(ResponseResults.apply)

  /**
   * Implicit JSON format for serializing and deserializing `RequestPing` objects.
   */
  implicit val requestPingFormat: JsonFormat[RequestPing] = jsonFormat3(RequestPing.apply)

  /**
   * Implicit JSON format for serializing and deserializing `ResponsePong` objects.
   */
  implicit val responsePongFormat: JsonFormat[ResponsePong] = jsonFormat4(ResponsePong.apply)

  /**
   * Converts a list of `Result` objects to a JSON string representing response results.
   *
   * @param results The list of `Result` objects to be converted to JSON.
   * @return A JSON string representing the response results.
   */
  def resultsToJson(results: List[Result]): String = {
    val json = JsObject(
      "message_type" -> JsString("response.results"),
      "results" -> JsArray(results.toVector.map { r =>
        JsObject(
          "position" -> JsNumber(r.position),
          "player" -> JsString(r.player.toString),
          "number" -> JsNumber(r.number),
          "result" -> JsNumber(r.result)
        )
      }: Vector[JsValue])
    )

    json.compactPrint
  }
}

