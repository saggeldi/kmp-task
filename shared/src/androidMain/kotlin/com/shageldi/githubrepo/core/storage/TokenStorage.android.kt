package com.shageldi.githubrepo.core.storage

import android.annotation.SuppressLint
import android.content.Context

actual class TokenStorage(private val context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    @SuppressLint("UseKtx")
    actual fun saveToken(token: String?) {
        prefs.edit().putString("access_token", token).apply()
    }

    actual fun getToken(): String? {
        return prefs.getString("access_token", null)
    }

    @SuppressLint("UseKtx")
    actual fun clearToken() {
        prefs.edit().remove("access_token").apply()
    }
}
