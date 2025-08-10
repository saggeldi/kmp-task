package com.shageldi.githubrepo.core.feature.auth.data.mapper

import com.shageldi.githubrepo.core.feature.auth.data.dto.UserApiDto
import com.shageldi.githubrepo.core.feature.auth.domain.model.UserModel

fun UserApiDto.toModel(): UserModel {
    return UserModel(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        name = name ?: "",
        nodeId = nodeId ?: ""
    )
}