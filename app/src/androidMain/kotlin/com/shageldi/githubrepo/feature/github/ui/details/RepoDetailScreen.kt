package com.shageldi.githubrepo.feature.github.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.shageldi.core_ui.component.AppScreenState
import com.shageldi.core_ui.component.AppTopBar
import com.shageldi.core_ui.component.EmptyMessage
import com.shageldi.githubrepo.R
import com.shageldi.githubrepo.feature.github.viewmodel.GithubViewModel
import com.shageldi.githubrepo.util.UiText
import com.shageldi.githubrepo.util.asString
import org.koin.androidx.compose.koinViewModel

interface RepoDetailScreenEvent {
    fun onBack()
    fun onLogout()
}

@Composable
fun RepoDetailScreen(
    repoName: String,
    fullName: String,
    event: RepoDetailScreenEvent,
    viewModel: GithubViewModel = koinViewModel()
) {
    val state = viewModel.singleRepoState.collectAsState()

    LaunchedEffect(true) {
        viewModel.initSingleRepo(fullName)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = repoName,
            modifier = Modifier.fillMaxWidth(),
            startAction = {
                IconButton(
                    onClick = {
                        event.onBack()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.content_description_back)
                    )
                }
            },
            endAction = {
                IconButton(
                    onClick = {
                        event.onLogout()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_logout_24),
                        contentDescription = "Logout"
                    )
                }
            }
        )

        AppScreenState(
            modifier = Modifier.fillMaxSize(),
            loading = state.value.loading,
            error = if (state.value.error != null) UiText.DynamicString(state.value.error!!).asString() else null,
            retryMessage = stringResource(R.string.retry),
            onRetry = {
                viewModel.getRepository(fullName)
            }
        ) {
            if (state.value.repo == null) {
                EmptyMessage(
                    modifier = Modifier.fillMaxSize(),
                    message = stringResource(R.string.empty),
                    retryMessage = stringResource(R.string.retry),
                    onRetry = {
                        viewModel.getRepository(fullName)
                    }
                )
            } else {
                state.value.repo?.let { data->
                    DetailContent(
                        modifier = Modifier.fillMaxSize(),
                        item = data
                    )
                }
            }
        }
    }
}