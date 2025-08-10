package com.shageldi.githubrepo.core.network

import com.shageldi.githubrepo.core.storage.TokenStorage
import io.ktor.client.HttpClient

expect fun httpClient(tokenStorage: TokenStorage): HttpClient