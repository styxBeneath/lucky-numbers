package io.luckynumbers
package messages

/**
 * Represents a "ping" request in the application.
 *
 * @param id           The unique identifier for the ping request.
 * @param message_type The type of the request (should be "request.ping").
 * @param timestamp    The timestamp associated with the ping request.
 */
case class RequestPing(id: Int, message_type: String, timestamp: Long)
