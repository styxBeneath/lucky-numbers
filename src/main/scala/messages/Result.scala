package io.luckynumbers
package messages

/**
 * Represents a result in the game.
 *
 * @param position The position of the player in the game.
 * @param player   The identifier of the player.
 * @param number   The generated number associated with the player.
 * @param result   The calculated result based on the generated number.
 */
case class Result(position: Int, player: Int, number: Int, result: Int)
