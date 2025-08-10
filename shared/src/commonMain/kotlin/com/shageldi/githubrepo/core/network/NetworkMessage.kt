package com.shageldi.githubrepo.core.network

import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val messageRu: String,
    val messageEn: String,
    val statusCode: Int,
    val statusDescription: String
) {
    companion object {
        fun generateMessage(message: String?): NetworkMessage {
            return if (message.isNullOrEmpty()) {
                NetworkMessage(
                    messageEn = "Unknown error.",
                    messageRu = "Неизвестная ошибка.",
                    statusCode = 500,
                    statusDescription = "Unknown error."
                )
            } else {
                NetworkMessage(
                    messageEn = message,
                    messageRu = message,
                    statusCode = 500,
                    statusDescription = "Unknown error."
                )
            }
        }
    }
}


fun HttpResponse.getErrorMessage(): NetworkMessage {
    val code = this.status.value
    val description = this.status.description
    return when {
        isUnauthorized() -> NetworkMessage(
            messageEn = "Unauthorized.",
            messageRu = "Неавторизованный запрос.",
            statusCode = code,
            statusDescription = description
        )

        isForbidden() -> NetworkMessage(
            messageEn = "Forbidden.",
            messageRu = "Запрещено.",
            statusCode = code,
            statusDescription = description
        )

        isNotFound() -> NetworkMessage(
            messageEn = "Not found.",
            messageRu = "Не найдено.",
            statusCode = code,
            statusDescription = description
        )

        isBadRequest() -> NetworkMessage(
            messageEn = "Bad request.",
            messageRu = "Неверный запрос.",
            statusCode = code,
            statusDescription = description
        )

        isConflict() -> NetworkMessage(
            messageEn = "Conflict.",
            messageRu = "Конфликт.",
            statusCode = code,
            statusDescription = description
        )

        isInternalServerError() -> NetworkMessage(
            messageEn = "Internal server error.",
            messageRu = "Внутренняя ошибка сервера.",
            statusCode = code,
            statusDescription = description
        )

        isBadGateway() -> NetworkMessage(
            messageEn = "Bad gateway.",
            messageRu = "Плохой шлюз.",
            statusCode = code,
            statusDescription = description
        )

        isServiceUnavailable() -> NetworkMessage(
            messageEn = "Service unavailable.",
            messageRu = "Сервис недоступен.",
            statusCode = code,
            statusDescription = description
        )

        isGatewayTimeout() -> NetworkMessage(
            messageEn = "Gateway timeout.",
            messageRu = "Шлюз не отвечает.",
            statusCode = code,
            statusDescription = description
        )

        else -> NetworkMessage(
            messageEn = "Unknown error.",
            messageRu = "Неизвестная ошибка.",
            statusCode = code,
            statusDescription = description
        )
    }
}