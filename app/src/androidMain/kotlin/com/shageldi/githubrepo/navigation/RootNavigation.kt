package com.shageldi.githubrepo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shageldi.githubrepo.feature.auth.ui.CheckScreen
import com.shageldi.githubrepo.feature.auth.ui.SignInEvent
import com.shageldi.githubrepo.feature.auth.ui.SignInScreen
import com.shageldi.githubrepo.feature.auth.viewmodel.AuthViewModel
import com.shageldi.githubrepo.feature.github.ui.RepoListEvent
import com.shageldi.githubrepo.feature.github.ui.RepoListScreen
import com.shageldi.githubrepo.feature.github.ui.details.RepoDetailScreen
import com.shageldi.githubrepo.feature.github.ui.details.RepoDetailScreenEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavigation() {
    val navHostController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val isLoggedIn = authViewModel.isLoggedState.collectAsState()

    // Этот код проверяет, вошел ли пользователь в систему перед началом навигации. Return добавлен потому что isLoggedIn.value может быть равным null на более медленных и старых устройствах Android
    if (isLoggedIn.value == null) {
        CheckScreen()
        return
    }

    fun logout() {
        authViewModel.logout()
        navHostController.navigate(Routes.SignInScreen) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    NavHost(navHostController, startDestination = if (isLoggedIn.value == true) Routes.RepoListScreen else Routes.SignInScreen) {
        composable<Routes.SignInScreen> {
            SignInScreen(
                authViewModel = authViewModel,
                object : SignInEvent {
                    override fun onDone() {
                        navHostController.navigate(Routes.RepoListScreen) {
                            popUpTo(Routes.SignInScreen) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable<Routes.RepoListScreen> {
            RepoListScreen(
                event = object : RepoListEvent {
                    override fun onRepoClick(repoName: String, fullName: String) {
                        navHostController.navigate(Routes.RepoDetailScreen(repoName, fullName))
                    }

                    override fun onLogout() {
                        logout()
                    }

                }
            )
        }

        composable<Routes.RepoDetailScreen> { backStack->
            val args = backStack.toRoute<Routes.RepoDetailScreen>()
            RepoDetailScreen(
                repoName = args.repoName,
                fullName = args.fullName,
                event = object : RepoDetailScreenEvent {
                    override fun onBack() {
                        navHostController.navigateUp()
                    }

                    override fun onLogout() {
                        logout()
                    }

                }
            )
        }

    }
}