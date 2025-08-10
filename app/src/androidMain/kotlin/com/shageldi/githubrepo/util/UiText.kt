package com.shageldi.githubrepo.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.shageldi.githubrepo.core.network.NetworkMessage

sealed class UiText {
    data class DynamicString(val message: NetworkMessage): UiText()
    data class LocalString(val stringsId: Int): UiText()
}

@Composable
fun UiText.asString(): String {
    val context = LocalContext.current
    val currentLanguage = LanguageUtils().getCurrentLanguage(context)
    return when(this) {
        is UiText.DynamicString -> when(currentLanguage) {
            "ru" -> message.messageRu
            else -> message.messageEn
        }
        is UiText.LocalString -> stringResource(stringsId)
    }
}

fun UiText.asString(context: Context): String {
    val currentLanguage = LanguageUtils().getCurrentLanguage(context)
    return when(this) {
        is UiText.DynamicString -> when(currentLanguage) {
            "ru" -> message.messageRu
            else -> message.messageEn
        }
        is UiText.LocalString -> context.getString(stringsId)
    }
}


