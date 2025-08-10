package com.shageldi.core_ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Составная функция для отображения настраиваемой кнопки, которая может отражать такие состояния как Idle, Loading, Success или Error.
 *
 * @param text Текст, отображаемый на кнопке.
 * @param onClick Функция обратного вызова, которая будет вызвана при нажатии на кнопку.
 * @param modifier Модификатор для макета и стилизации кнопки.
 * @param state Текущее состояние кнопки, определяющее ее внешний вид. По умолчанию [ComponentState.Idle].
 * @param enabled Указывает, включена или отключена кнопка. По умолчанию true.
 * @param icon Опциональная иконка для отображения рядом с текстом кнопки. По умолчанию null.
 * @param colors Определяет цветовую схему кнопки в различных состояниях. По умолчанию используется основная цветовая схема.
 * @param autoResetErrorDelay Длительность в миллисекундах для автоматического сброса кнопки из состояния ошибки в idle. По умолчанию 3000мс.
 * @param showErrorMessage Указывает, показывать ли сообщение об ошибке на кнопке в состоянии ошибки. По умолчанию true.
 * @param maxErrorMessageLines Максимальное количество строк для отображения сообщения об ошибке. По умолчанию 1.
 * @param shape Форма кнопки, определяемая [RoundedCornerShape]. По умолчанию радиус закругления 12dp.
  */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ComponentState = ComponentState.Idle,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    autoResetErrorDelay: Long = 3000L,
    showErrorMessage: Boolean = true,
    maxErrorMessageLines: Int = 1,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    var internalState by remember { mutableStateOf(state) }

    LaunchedEffect(state) {
        internalState = state
    }

    LaunchedEffect(internalState) {
        if (internalState is ComponentState.Error && autoResetErrorDelay > 0) {
            delay(autoResetErrorDelay)
            internalState = ComponentState.Idle
        }
    }

    val isLoading = internalState is ComponentState.Loading
    val isEnabled = enabled && !isLoading

    val animatedContainerColor by animateColorAsState(
        targetValue = getContainerColor(internalState, colors),
        animationSpec = tween(durationMillis = 300),
        label = "containerColor"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = getContentColor(internalState, colors),
        animationSpec = tween(durationMillis = 300),
        label = "contentColor"
    )

    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = isEnabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedContainerColor,
            contentColor = animatedContentColor
        )
    ) {
        AnimatedContent(
            targetState = internalState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "buttonContent"
        ) { currentState ->
            ButtonContent(
                state = currentState,
                text = text,
                icon = icon,
                showErrorMessage = showErrorMessage,
                maxErrorMessageLines = maxErrorMessageLines,
                contentColor = animatedContentColor
            )
        }
    }
}

@Composable
private fun ButtonContent(
    state: ComponentState,
    text: String,
    icon: ImageVector?,
    showErrorMessage: Boolean,
    maxErrorMessageLines: Int,
    contentColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        when (state) {
            is ComponentState.Loading -> {
                LoadingContent(contentColor = contentColor)
            }
            is ComponentState.Success -> {
                SuccessContent(contentColor = contentColor)
            }
            is ComponentState.Error -> {
                ErrorContent(
                    error = state,
                    showErrorMessage = showErrorMessage,
                    maxErrorMessageLines = maxErrorMessageLines,
                    contentColor = contentColor
                )
            }
            is ComponentState.Idle -> {
                IdleContent(
                    text = text,
                    icon = icon,
                    contentColor = contentColor
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(contentColor: Color) {
    CircularProgressIndicator(
        modifier = Modifier.size(16.dp),
        strokeWidth = 2.dp,
        color = contentColor
    )
}

@Composable
private fun SuccessContent(contentColor: Color) {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Success",
        modifier = Modifier.size(16.dp),
        tint = contentColor
    )
}

@Composable
private fun ErrorContent(
    error: ComponentState.Error,
    showErrorMessage: Boolean,
    maxErrorMessageLines: Int,
    contentColor: Color
) {
    Icon(
        painter = painterResource(com.shageldi.core_ui.R.drawable.outline_error_24),
        contentDescription = "Error",
        modifier = Modifier.size(16.dp),
        tint = contentColor
    )

    if (showErrorMessage && error.message.isNotBlank()) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = error.message,
            color = contentColor,
            maxLines = maxErrorMessageLines,
            overflow = TextOverflow.Ellipsis
        )
    } else if (!showErrorMessage || error.message.isBlank()) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Error",
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun IdleContent(
    text: String,
    icon: ImageVector?,
    contentColor: Color
) {
    if (icon != null) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
    Text(
        text = text,
        color = contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun getContainerColor(
    state: ComponentState,
    defaultColors: ButtonColors
): Color {
    return when (state) {
        is ComponentState.Success -> MaterialTheme.colorScheme.tertiary
        is ComponentState.Error -> MaterialTheme.colorScheme.error
        is ComponentState.Loading -> MaterialTheme.colorScheme.primary
        else -> defaultColors.containerColor
    }
}

@Composable
private fun getContentColor(
    state: ComponentState,
    defaultColors: ButtonColors
): Color {
    return when (state) {
        is ComponentState.Success -> MaterialTheme.colorScheme.onTertiary
        is ComponentState.Error -> MaterialTheme.colorScheme.onError
        is ComponentState.Loading -> MaterialTheme.colorScheme.onSurface
        else -> defaultColors.contentColor
    }
}


@Preview
@Composable
private fun AppButtonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var state1 by remember { mutableStateOf<ComponentState>(ComponentState.Idle) }
            var state2 by remember { mutableStateOf<ComponentState>(ComponentState.Loading) }
            var state3 by remember { mutableStateOf<ComponentState>(ComponentState.Success) }
            var state4 by remember { mutableStateOf<ComponentState>(ComponentState.Error("Network error occurred")) }

            AppButton(
                text = "Click me",
                onClick = {
                    state1 = ComponentState.Loading
                },
                state = state1,
                modifier = Modifier.fillMaxWidth()
            )

            AppButton(
                text = "Loading Button",
                onClick = { },
                state = state2,
                modifier = Modifier.fillMaxWidth()
            )

            AppButton(
                text = "Success Button",
                onClick = { state3 = ComponentState.Idle },
                state = state3,
                modifier = Modifier.fillMaxWidth()
            )

            AppButton(
                text = "Error Button",
                onClick = { state4 = ComponentState.Idle },
                state = state4,
                modifier = Modifier.fillMaxWidth(),
                autoResetErrorDelay = 0L
            )
        }
    }
}
