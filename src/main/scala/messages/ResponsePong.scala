package io.luckynumbers
package messages

/**
 * Represents a "pong" response in the application.
 *
 * @param message_type The type of the response (should be "response.pong").
 * @param id           The unique identifier associated with the pong response.
 * @param request_at   The timestamp of the corresponding "ping" request.
 * @param timestamp    The timestamp of the pong response.
 */
case class ResponsePong(message_type: String, id: Int, request_at: Long, timestamp: Long)
