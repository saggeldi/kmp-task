package com.shageldi.githubrepo.core.network

import io.ktor.client.statement.HttpResponse


fun HttpResponse.isSuccessful(): Boolean {
    return this.status.value in 200..299
}

fun HttpResponse.isUnauthorized(): Boolean {
    return this.status.value == 401
}

fun HttpResponse.isForbidden(): Boolean {
    return this.status.value == 403
}

fun HttpResponse.isNotFound(): Boolean {
    return this.status.value == 404
}

fun HttpResponse.isBadRequest(): Boolean {
    return this.status.value == 400
}

fun HttpResponse.isConflict(): Boolean {
    return this.status.value == 409
}

fun HttpResponse.isInternalServerError(): Boolean {
    return this.status.value == 500
}

fun HttpResponse.isBadGateway(): Boolean {
    return this.status.value == 502
}

fun HttpResponse.isServiceUnavailable(): Boolean {
    return this.status.value == 503
}

fun HttpResponse.isGatewayTimeout(): Boolean {
    return this.status.value == 504
}
