package com.shageldi.core_ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shageldi.core_ui.state.LocalSnackBar

@Composable
fun SafeContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalSnackBar provides snackBarHostState
        ) {
            Box(Modifier.padding(paddingValues).consumeWindowInsets(paddingValues).imePadding()) {
                SnackbarHost(
                    hostState = snackBarHostState,
                    snackbar = { snackData ->
                        Snackbar(
                            snackData,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        )
                    }
                )
                content()
            }
        }
    }

}