package com.shageldi.githubrepo.core.feature.auth.domain

import com.shageldi.githubrepo.core.feature.auth.domain.model.UserModel
import com.shageldi.githubrepo.core.network.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithToken(token: String): Flow<Result<UserModel>>
    suspend fun logout()
    fun getToken(): String
    fun isLoggedIn(): Boolean
}