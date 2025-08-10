package com.shageldi.githubrepo.core.feature.github.domain.model

data class SingleRepoModel(
    val id: Int,
    val fullName: String,
    val title: String,
    val description: String,
    val language: String,
    val license: String,
    val licenseUrl: String,
    val starsCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val siteUrl: String,
    val markdown: String
) {
    fun hasLicense(): Boolean {
        return license.trim().isNotEmpty() && licenseUrl.trim().isNotEmpty()
    }

    fun hasDescription(): Boolean {
        return description.trim().isNotEmpty()
    }

    fun hasLanguage(): Boolean {
        return language.trim().isNotEmpty()
    }

    fun hasSiteUrl(): Boolean {
        return siteUrl.trim().isNotEmpty()
    }

    fun hasMarkdown(): Boolean {
        return markdown.trim().isNotEmpty()
    }
}
