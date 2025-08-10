package com.shageldi.core_ui.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalDarkMode = compositionLocalOf {
    mutableStateOf(true)
}