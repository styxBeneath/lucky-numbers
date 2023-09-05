package io.luckynumbers
package util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JSONFormatter {

  def resultsToJson(results: List[Result]): String = {
    // Initialize the ObjectMapper
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)

    // Create a JSON object with the desired structure
    val jsonMap = Map(
      "message_type" -> "response.results",
      "results" -> results.map { r =>
        Map(
          "position" -> r.position,
          "player" -> r.player.toString, // Convert player to String as specified in your example
          "number" -> r.number,
          "result" -> r.result
        )
      }
    )

    // Serialize the JSON object to a string
    objectMapper.writeValueAsString(jsonMap)
  }
}
