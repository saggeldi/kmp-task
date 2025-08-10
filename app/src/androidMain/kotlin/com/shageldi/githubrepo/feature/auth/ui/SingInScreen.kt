package com.shageldi.githubrepo.feature.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shageldi.core_ui.component.AppButton
import com.shageldi.core_ui.component.ComponentState
import com.shageldi.core_ui.state.LocalSnackBar
import com.shageldi.githubrepo.R
import com.shageldi.githubrepo.feature.auth.viewmodel.AuthViewModel
import com.shageldi.githubrepo.util.UiEvent
import com.shageldi.githubrepo.util.UiText
import com.shageldi.githubrepo.util.asString
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

interface SignInEvent {
    fun onDone()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = koinViewModel(),
    event: SignInEvent
) {
    val snackBar = LocalSnackBar.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    val signInState = authViewModel.signInState.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(true) {
        authViewModel.uiChannel.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    snackBar.showSnackbar(
                        message = event.text.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Icon(
            painter = painterResource(R.drawable.github_mark),
            contentDescription = stringResource(R.string.content_description_github),
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        val isError = remember {
            mutableStateOf(true)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = authViewModel.token.value,
                onValueChange = {
                    authViewModel.updateToken(it)
                    isError.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                label = {
                    Text(stringResource(id = R.string.github_token_label))
                },
                isError = signInState.value.error != null && isError.value,
                placeholder = {
                    Text(stringResource(id = R.string.github_token_placeholder))
                },
                supportingText = {
                    Text(
                        text = if (signInState.value.error != null && isError.value)
                            UiText.DynamicString(signInState.value.error!!).asString()
                        else stringResource(R.string.github_token_helper)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        authViewModel.singIn(
                            onSuccess = {
                                event.onDone()
                            }
                        )
                    }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )


        }

        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            onClick = {
                if (authViewModel.token.value.trim().isNotEmpty()) {
                    keyboardController?.hide()
                    isError.value = true
                    authViewModel.singIn(
                        onSuccess = {
                            event.onDone()
                        }
                    )
                } else {
                    coroutineScope.launch {
                        snackBar.showSnackbar(context.getString(R.string.sign_in_token_is_empty))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.sign_in_button),
            state = if (signInState.value.loading)
                ComponentState.Loading
            else if (signInState.value.error != null) ComponentState.Error(
                stringResource(R.string.sign_in_error_button)
            ) else ComponentState.Idle
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
