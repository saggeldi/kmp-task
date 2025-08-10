package com.shageldi.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Компонент для отображения верхней панели приложения.
 *
 * @param modifier Модификатор для настройки компонента
 * @param title Заголовок, отображаемый в центре панели
 * @param startAction Компонуемая функция для отображения действия в начале панели (необязательно)
 * @param endAction Компонуемая функция для отображения действия в конце панели (необязательно)
 */
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    startAction: @Composable (() -> Unit)? = null,
    endAction: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (startAction != null) {
                startAction()
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            if (endAction != null) {
                endAction()
            }

        }

        HorizontalDivider()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppTopBarPreview() {
    SafeContent {
        AppTopBar(
            title = "Test",
            modifier = Modifier.fillMaxWidth(),
            startAction = {
                Text("Start")
            },
            endAction = {
                Text("End")
            }
        )
    }
}