package com.shageldi.githubrepo.core.feature.auth.data

import com.shageldi.githubrepo.core.feature.auth.data.dto.UserApiDto
import com.shageldi.githubrepo.core.feature.auth.data.mapper.toModel
import com.shageldi.githubrepo.core.feature.auth.domain.AuthRepository
import com.shageldi.githubrepo.core.feature.auth.domain.model.UserModel
import com.shageldi.githubrepo.core.network.NetworkMessage
import com.shageldi.githubrepo.core.network.Result
import com.shageldi.githubrepo.core.network.getErrorMessage
import com.shageldi.githubrepo.core.network.isSuccessful
import com.shageldi.githubrepo.core.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    override suspend fun signInWithToken(token: String): Flow<Result<UserModel>> = flow {
        emit(Result.Loading())
        tokenStorage.clearToken()
        try {
            val response = client.get("user") {
                headers.append("Authorization", "Bearer $token")
            }
            if (response.isSuccessful()) {
                val body = response.body<UserApiDto>()
                tokenStorage.saveToken(token)
                emit(Result.Success(body.toModel()))
            } else {
                emit(Result.Error(response.getErrorMessage()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Result.Error(NetworkMessage.generateMessage(ex.message)))
        }
    }

    override suspend fun logout() {
        tokenStorage.clearToken()
    }

    override fun getToken(): String {
        return tokenStorage.getToken() ?: ""
    }

    override fun isLoggedIn(): Boolean {
        return tokenStorage.getToken().isNullOrEmpty().not()
    }
}