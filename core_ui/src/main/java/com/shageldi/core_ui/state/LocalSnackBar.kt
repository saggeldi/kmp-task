package com.shageldi.core_ui.state

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackBar = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }