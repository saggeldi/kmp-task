package com.shageldi.githubrepo.core.network

import com.shageldi.githubrepo.core.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(tokenStorage: TokenStorage): HttpClient = HttpClient(Darwin) {

}