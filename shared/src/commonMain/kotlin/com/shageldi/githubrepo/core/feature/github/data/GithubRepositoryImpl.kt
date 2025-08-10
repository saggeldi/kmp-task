package com.shageldi.githubrepo.core.feature.github.data

import com.shageldi.githubrepo.core.feature.github.data.dto.list.GithubRepoDtoItem
import com.shageldi.githubrepo.core.feature.github.data.dto.single.RepositoryDto
import com.shageldi.githubrepo.core.feature.github.data.mapper.toModel
import com.shageldi.githubrepo.core.feature.github.domain.GithubRepository
import com.shageldi.githubrepo.core.feature.github.domain.model.RepoListModel
import com.shageldi.githubrepo.core.feature.github.domain.model.SingleRepoModel
import com.shageldi.githubrepo.core.network.NetworkMessage
import com.shageldi.githubrepo.core.network.Result
import com.shageldi.githubrepo.core.network.getErrorMessage
import com.shageldi.githubrepo.core.network.isSuccessful
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubRepositoryImpl(
    private val httpClient: HttpClient,
    private val readmeApi: ReadmeApi
): GithubRepository {
    override suspend fun getRepositories(
        perPage: Int,
        page: Int,
        sort: String
    ): Flow<Result<List<RepoListModel>>> = flow {
        emit(Result.Loading())
        try {
            val response = httpClient.get("user/repos") {
                url {
                    parameter("sort", sort)
                    parameter("per_page", perPage)
                    parameter("page", page)
                }
            }

            if (response.isSuccessful()) {
                val body = response.body<List<GithubRepoDtoItem>>()
                emit(Result.Success(body.map { it.toModel() }))
            } else {
                emit(Result.Error(response.getErrorMessage()))
            }

        } catch (ex: Exception) {
            emit(Result.Error(NetworkMessage.generateMessage(ex.message)))
        }
    }

    override suspend fun getRepository(fullName: String): Flow<Result<SingleRepoModel>> = flow {
        emit(Result.Loading())
        try {
            val response = httpClient.get("repos/$fullName")
            if (response.isSuccessful()) {
                val body = response.body<RepositoryDto>()
                val readme = readmeApi.getReadme(fullName)
                emit(Result.Success(body.toModel(readme)))
            } else {
                emit(Result.Error(response.getErrorMessage()))
            }
        } catch (ex: Exception) {
            emit(Result.Error(NetworkMessage.generateMessage(ex.message)))
        }
    }
}