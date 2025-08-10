package com.shageldi.githubrepo.core.feature.github.data.mapper

import com.shageldi.githubrepo.core.feature.github.data.dto.single.RepositoryDto
import com.shageldi.githubrepo.core.feature.github.domain.model.SingleRepoModel

fun RepositoryDto.toModel(markdown: String): SingleRepoModel {
    return SingleRepoModel(
        id = id?:0,
        fullName = fullName?:"",
        title = name?:"",
        description = description?:"",
        language = language?:"markdown",
        license = license?.name?:"Unknown",
        licenseUrl = license?.url?:"",
        starsCount = stargazersCount?:0,
        watchersCount = watchersCount?:0,
        forksCount = forksCount?:0,
        siteUrl = htmlUrl?:"",
        markdown = markdown
    )
}