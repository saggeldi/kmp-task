package com.shageldi.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shageldi.core_ui.R


/**
 * Компонент для отображения состояния экрана.
 *
 * @param modifier Модификатор для настройки компонента
 * @param loading Флаг загрузки, если true - отображается индикатор загрузки
 * @param error Текст ошибки, если не null - отображается экран с ошибкой
 * @param retryMessage Текст кнопки повтора
 * @param onRetry Обработчик нажатия кнопки повтора
 * @param content Содержимое экрана при отсутствии ошибки и загрузки
 */
@Composable
fun AppScreenState(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    error: String? = null,
    retryMessage: String = "Retry",
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
       if (loading) {
           CircularProgressIndicator(Modifier
               .size(35.dp)
               .align(Alignment.Center), color = MaterialTheme.colorScheme.onSurface)
       } else if (error != null) {
           Column(
               modifier = Modifier.align(Alignment.Center),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(12.dp)
           ) {
               Image(
                   painter = painterResource(id = R.drawable.error),
                   contentDescription = null,
                   modifier = Modifier.size(120.dp)
               )
               Text(
                   text = error,
                   color = MaterialTheme.colorScheme.onSurface,
                   modifier = Modifier.fillMaxWidth(0.7f),
                   textAlign = TextAlign.Center,
                   maxLines = 2,
                   overflow = TextOverflow.Ellipsis
               )
               OutlinedButton(
                   onClick = onRetry
               ) {
                   Row(
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.spacedBy(4.dp)
                   ) {
                       Icon(
                           painter = painterResource(id = R.drawable.outline_refresh_24),
                           contentDescription = "Retry",
                           tint = MaterialTheme.colorScheme.onSurface
                       )
                       Text(
                           text = retryMessage,
                           color = MaterialTheme.colorScheme.onSurface
                       )
                   }
               }
           }
       } else {
           content()
       }
    }
}