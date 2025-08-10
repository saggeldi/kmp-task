package com.shageldi.githubrepo.core.feature.auth.domain.model

data class UserModel(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val name: String,
    val nodeId: String
)
