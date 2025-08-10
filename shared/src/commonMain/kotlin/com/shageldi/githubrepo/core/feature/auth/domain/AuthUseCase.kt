package com.shageldi.githubrepo.core.feature.auth.domain

import com.shageldi.githubrepo.core.feature.auth.domain.model.UserModel
import com.shageldi.githubrepo.core.network.Result
import kotlinx.coroutines.flow.Flow

class AuthUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: String): Flow<Result<UserModel>> {
        return authRepository.signInWithToken(token)
    }

    suspend fun logout() {
        authRepository.logout()
    }

    fun getToken(): String {
        return authRepository.getToken()
    }

    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }
}