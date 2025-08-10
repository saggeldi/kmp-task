package com.shageldi.githubrepo.feature.auth.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shageldi.githubrepo.R
import com.shageldi.githubrepo.core.feature.auth.domain.AuthUseCase
import com.shageldi.githubrepo.core.network.Result
import com.shageldi.githubrepo.feature.auth.state.SignInState
import com.shageldi.githubrepo.util.ToastType
import com.shageldi.githubrepo.util.UiEvent
import com.shageldi.githubrepo.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCase: AuthUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _singInState = MutableStateFlow(SignInState())
    val signInState = _singInState.asStateFlow()

    private val _isLoggedState = MutableStateFlow<Boolean?>(null)
    val isLoggedState = _isLoggedState.asStateFlow()

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    var token = mutableStateOf(savedStateHandle["token"] ?: authUseCase.getToken())
        private set

    init {
        viewModelScope.launch {
            if (isLoggedIn()) {
                singIn(
                    onSuccess = {
                        _isLoggedState.value = true
                    },
                    onError = {
                        _isLoggedState.value = false
                    },
                    showMessage = false
                )
            } else {
                _isLoggedState.value = false
            }
        }
    }





    fun updateToken(newToken: String) {
        token.value = newToken
        savedStateHandle["token"] = newToken
    }

    fun logout() {
        viewModelScope.launch {
            savedStateHandle["token"] = null
            token.value = ""
            authUseCase.logout()
            _isLoggedState.value = false
        }
    }

    fun isLoggedIn(): Boolean {
        return authUseCase.isLoggedIn()
    }



    fun singIn(onSuccess: () -> Unit, onError: () -> Unit = {}, showMessage: Boolean = true) {
        viewModelScope.launch {
            authUseCase(token.value).onEach { result ->
                when(result) {
                    is Result.Error -> {
                        _singInState.update {
                            it.copy(
                                loading = false,
                                error = result.localizedMessage
                            )
                        }

                        if (showMessage) {
                            if (result.localizedMessage != null) {
                                _uiChannel.send(UiEvent.ShowToast(UiText.DynamicString(result.localizedMessage!!), ToastType.Error))
                            } else {
                                _uiChannel.send(UiEvent.ShowToast(UiText.LocalString(R.string.unknown_error), ToastType.Error))
                            }
                        }

                        onError()
                    }
                    is Result.Loading -> {
                        _singInState.update {
                            it.copy(
                                loading = true,
                                error = result.localizedMessage
                            )
                        }
                    }
                    is Result.Success -> {
                        _singInState.update {
                            it.copy(
                                loading = false,
                                result = result.data
                            )
                        }
                        onSuccess()
                        if (showMessage) {
                            _uiChannel.send(
                                UiEvent.ShowToast(
                                    UiText.LocalString(R.string.sing_in_success),
                                    ToastType.Success
                                )
                            )
                        }


                    }
                }
            }.launchIn(this)
        }
    }
}