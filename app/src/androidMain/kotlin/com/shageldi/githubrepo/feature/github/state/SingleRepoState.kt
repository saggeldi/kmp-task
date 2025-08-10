package com.shageldi.githubrepo.feature.github.state

import com.shageldi.githubrepo.core.feature.github.domain.model.SingleRepoModel
import com.shageldi.githubrepo.core.network.NetworkMessage

data class SingleRepoState(
    val loading: Boolean = false,
    val error: NetworkMessage? = null,
    val repo: SingleRepoModel? = null
)
