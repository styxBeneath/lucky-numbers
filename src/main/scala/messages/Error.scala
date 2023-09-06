package io.luckynumbers
package messages

/**
 * Represents an error message in the application.
 *
 * @param message_type  The type of the error message.
 * @param error_message The detailed error message.
 */
case class Error(message_type: String, error_message: String)
