package com.shageldi.githubrepo.feature.github.state

import com.shageldi.githubrepo.core.feature.github.domain.model.RepoListModel
import com.shageldi.githubrepo.core.network.NetworkMessage

data class ListState(
    val loading: Boolean = false,
    val error: NetworkMessage? = null,
    val repos: List<RepoListModel>? = emptyList()
)
