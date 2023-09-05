package io.luckynumbers
package messages

case class ResponsePong(message_type: String, id: Int, request_at: Long, timestamp: Long)
