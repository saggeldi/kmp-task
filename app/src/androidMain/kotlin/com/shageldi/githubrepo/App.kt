package com.shageldi.githubrepo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.shageldi.core_ui.component.SafeContent
import com.shageldi.core_ui.state.LocalDarkMode
import com.shageldi.core_ui.theme.AppTheme
import com.shageldi.githubrepo.navigation.RootNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    CompositionLocalProvider(LocalDarkMode provides remember { mutableStateOf(true) }) {
        AppTheme(darkTheme = LocalDarkMode.current.value) {
            SafeContent {
                RootNavigation()
            }
        }
    }

}