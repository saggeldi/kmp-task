package com.shageldi.core_ui.component

sealed class ComponentState {
    object Idle : ComponentState()
    object Loading : ComponentState()
    object Success : ComponentState()
    data class Error(val message: String) : ComponentState()
}