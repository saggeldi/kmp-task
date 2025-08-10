package com.shageldi.githubrepo.core.feature.github.domain

import com.shageldi.githubrepo.core.feature.github.domain.model.RepoListModel
import com.shageldi.githubrepo.core.feature.github.domain.model.SingleRepoModel
import com.shageldi.githubrepo.core.network.Result
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getRepositories(perPage: Int, page: Int, sort: String): Flow<Result<List<RepoListModel>>>
    suspend fun getRepository(fullName: String): Flow<Result<SingleRepoModel>>
}