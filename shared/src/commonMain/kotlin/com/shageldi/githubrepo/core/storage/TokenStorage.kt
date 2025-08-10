package com.shageldi.githubrepo.core.storage

expect class TokenStorage {
    fun saveToken(token: String?)
    fun getToken(): String?
    fun clearToken()
}
