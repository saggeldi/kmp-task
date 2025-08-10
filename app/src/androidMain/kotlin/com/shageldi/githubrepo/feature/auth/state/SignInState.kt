package com.shageldi.githubrepo.feature.auth.state

import com.shageldi.githubrepo.core.feature.auth.domain.model.UserModel
import com.shageldi.githubrepo.core.network.NetworkMessage

data class SignInState(
    val loading: Boolean = false,
    val error: NetworkMessage? = null,
    val result: UserModel? = null
)
