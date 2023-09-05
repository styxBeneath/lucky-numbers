package io.luckynumbers
package util

import messages.{Error, RequestPing, RequestPlay, ResponsePong, ResponseResults, Result}
import spray.json._
import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, listFormat, LongJsonFormat, jsonFormat2, jsonFormat3, jsonFormat4}

object JSONUtil {

  implicit val errorFormat: JsonFormat[Error] = jsonFormat2(Error.apply)
  implicit val requestPlayFormat: JsonFormat[RequestPlay] = jsonFormat2(RequestPlay.apply)
  implicit val resultFormat: JsonFormat[Result] = jsonFormat4(Result.apply)
  implicit val responseResultsFormat: JsonFormat[ResponseResults] = jsonFormat2(ResponseResults.apply)
  implicit val requestPingFormat: JsonFormat[RequestPing] = jsonFormat3(RequestPing.apply)
  implicit val responsePongFormat: JsonFormat[ResponsePong] = jsonFormat4(ResponsePong.apply)

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
