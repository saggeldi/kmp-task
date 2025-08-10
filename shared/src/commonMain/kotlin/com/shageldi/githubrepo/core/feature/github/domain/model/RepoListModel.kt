package com.shageldi.githubrepo.core.feature.github.domain.model

data class RepoListModel(
    val id: Int,
    val fullName: String,
    val title: String,
    val description: String,
    val language: String
)
