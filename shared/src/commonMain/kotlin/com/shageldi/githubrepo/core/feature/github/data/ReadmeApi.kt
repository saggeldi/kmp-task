package com.shageldi.githubrepo.core.feature.github.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText

class ReadmeApi(
    private val httpClient: HttpClient
) {
    suspend fun getReadme(fullName: String): String {
        val response = httpClient.get("repos/$fullName/readme") {
            header("Accept", "application/vnd.github.raw+json")
        }
        return response.bodyAsText()
    }
}