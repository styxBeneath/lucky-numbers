package io.luckynumbers
package messages

/**
 * Represents a "play" request in the application.
 *
 * @param message_type The type of the request (should be "request.play").
 * @param players      The number of players in the game.
 */
case class RequestPlay(message_type: String, players: Int)
