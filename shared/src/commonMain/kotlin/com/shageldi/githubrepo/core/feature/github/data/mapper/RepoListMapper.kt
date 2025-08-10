package com.shageldi.githubrepo.core.feature.github.data.mapper

import com.shageldi.githubrepo.core.feature.github.data.dto.list.GithubRepoDtoItem
import com.shageldi.githubrepo.core.feature.github.domain.model.RepoListModel

fun GithubRepoDtoItem.toModel(): RepoListModel {
    return RepoListModel(
        id = id ?: 0,
        fullName = fullName?:"",
        title = name?:"",
        description = description?:"",
        language = language?:"markdown"
    )
}