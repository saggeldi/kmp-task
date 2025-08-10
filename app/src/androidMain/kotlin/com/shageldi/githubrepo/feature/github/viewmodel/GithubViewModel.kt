package com.shageldi.githubrepo.feature.github.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shageldi.githubrepo.core.feature.github.domain.GithubUseCase
import com.shageldi.githubrepo.core.network.Result
import com.shageldi.githubrepo.feature.github.state.ListState
import com.shageldi.githubrepo.feature.github.state.SingleRepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GithubViewModel(
    private val githubUseCase: GithubUseCase
): ViewModel() {
    private val _listState = MutableStateFlow(ListState())
    val listState = _listState.asStateFlow()

    private val _singleRepoState = MutableStateFlow(SingleRepoState())
    val singleRepoState = _singleRepoState.asStateFlow()


    fun initRepositories() {
        if (listState.value.repos.isNullOrEmpty()) {
            getRepositories()
        }
    }

    fun initSingleRepo(fullName: String) {
        if (singleRepoState.value.repo == null) {
            getRepository(fullName)
        }
    }


    fun getRepositories() {
        viewModelScope.launch {
            githubUseCase.getRepositories(
                perPage = 10,
                page = 1,
                sort = "updated"
            ).onEach { result ->
                when(result) {
                    is Result.Error -> {
                        _listState.update { it.copy(error = result.localizedMessage, loading = false) }
                    }
                    is Result.Loading -> {
                        _listState.update { it.copy(loading = true, error = null) }
                    }
                    is Result.Success -> {
                        _listState.update { it.copy(repos = result.data, loading = false) }
                    }
                }
            }.launchIn(this)
        }
    }


    fun getRepository(fullName: String) {
        viewModelScope.launch {
            githubUseCase.getRepository(fullName).onEach { result ->
                when (result) {
                    is Result.Error -> {
                        _singleRepoState.update { it.copy(error = result.localizedMessage, loading = false) }
                    }
                    is Result.Loading -> {
                        _singleRepoState.update { it.copy(loading = true, error = null) }
                    }
                    is Result.Success -> {
                        _singleRepoState.update { it.copy(repo = result.data, loading = false) }
                    }
                }
            }.launchIn(this)
        }
    }
}