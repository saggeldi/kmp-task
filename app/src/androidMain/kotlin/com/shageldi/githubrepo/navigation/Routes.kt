package com.shageldi.githubrepo.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SignInScreen: Routes()

    @Serializable
    data object RepoListScreen: Routes()

    @Serializable
    data class RepoDetailScreen(val repoName: String, val fullName: String): Routes()

}