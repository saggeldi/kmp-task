package com.shageldi.githubrepo.core.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults

actual class TokenStorage {
    actual fun saveToken(token: String?) {
        if (token != null) {
            NSUserDefaults.standardUserDefaults.setObject(token, "access_token")
        } else {
            NSUserDefaults.standardUserDefaults.removeObjectForKey("access_token")
        }
    }

    actual fun getToken(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey("access_token")
    }

    actual fun clearToken() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey("access_token")
        NSUserDefaults.standardUserDefaults.removeObjectForKey("refresh_token")
    }
}