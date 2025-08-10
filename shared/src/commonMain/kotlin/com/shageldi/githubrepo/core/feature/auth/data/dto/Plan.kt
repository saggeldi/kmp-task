package com.shageldi.githubrepo.core.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Plan(
    @SerialName("collaborators")
    val collaborators: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("private_repos")
    val privateRepos: Int? = null,
    @SerialName("space")
    val space: Int? = null
)