package com.shageldi.githubrepo.feature.github.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shageldi.core_ui.component.AppScreenState
import com.shageldi.core_ui.component.AppTopBar
import com.shageldi.core_ui.component.EmptyMessage
import com.shageldi.githubrepo.R
import com.shageldi.githubrepo.feature.github.viewmodel.GithubViewModel
import com.shageldi.githubrepo.util.UiText
import com.shageldi.githubrepo.util.asString
import org.koin.androidx.compose.koinViewModel


interface RepoListEvent {
    fun onRepoClick(repoName: String, fullName: String)
    fun onLogout()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListScreen(
    viewModel: GithubViewModel = koinViewModel(),
    event: RepoListEvent
) {

    val state = viewModel.listState.collectAsState()

    LaunchedEffect(true) {
        viewModel.initRepositories()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(R.string.repos),
            modifier = Modifier.fillMaxWidth(),
            endAction = {
                IconButton(
                    onClick = {
                        event.onLogout()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_logout_24),
                        contentDescription = stringResource(R.string.content_description_logout)
                    )
                }
            }
        )

        PullToRefreshBox(
            isRefreshing = state.value.loading,
            onRefresh = {
                viewModel.getRepositories()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            AppScreenState(
                modifier = Modifier.fillMaxSize(),
                loading = state.value.loading,
                error = if (state.value.error != null) UiText.DynamicString(state.value.error!!)
                    .asString() else null,
                retryMessage = stringResource(R.string.retry),
                onRetry = {
                    viewModel.getRepositories()
                }
            ) {
                if (state.value.repos.isNullOrEmpty()) {
                    EmptyMessage(
                        modifier = Modifier.fillMaxSize(),
                        message = stringResource(R.string.empty),
                        retryMessage = stringResource(R.string.retry),
                        onRetry = {
                            viewModel.getRepositories()
                        }
                    )
                } else {
                    state.value.repos!!.let { list ->
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(list.count()) { index ->
                                val item = list[index]
                                RepoListItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = item
                                ) {
                                    event.onRepoClick(item.title, item.fullName)
                                }

                                if (index < list.lastIndex)
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outline.copy(
                                            alpha = 0.2f
                                        )
                                    )
                            }
                        }
                    }
                }
            }
        }
    }


}