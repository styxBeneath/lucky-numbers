package io.luckynumbers
package messages

/**
 * Represents a response containing game results in the application.
 *
 * @param message_type The type of the response (should be "response.results").
 * @param results      A list of "Result" objects representing the game results.
 */
case class ResponseResults(message_type: String, results: List[Result])
