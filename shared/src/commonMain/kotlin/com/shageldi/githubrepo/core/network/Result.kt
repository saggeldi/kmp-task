package com.shageldi.githubrepo.core.network


sealed class Result<T>(
    val data: T? = null,
    val localizedMessage: NetworkMessage? = null,
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(
        localizedMessage: NetworkMessage? = null,
        data: T? = null,
    ) : Result<T>(data, localizedMessage)

    class Loading<T>(val isLoading: Boolean = true) : Result<T>(null)
}