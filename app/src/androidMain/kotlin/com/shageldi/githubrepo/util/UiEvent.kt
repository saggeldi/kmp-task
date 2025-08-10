package com.shageldi.githubrepo.util

enum class ToastType {
    Success,
    Error,
    Warning,
    Info
}

sealed class UiEvent {
    object OnDone : UiEvent()
    data class ShowToast(val text: UiText, val type: ToastType = ToastType.Error): UiEvent()
}