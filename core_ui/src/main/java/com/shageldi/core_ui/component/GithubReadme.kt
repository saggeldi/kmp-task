package com.shageldi.core_ui.component

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin

@Composable
fun GithubReadme(modifier: Modifier, markdownContent: String, isDarkMode: Boolean = true) {

    val context = LocalContext.current
    val markwon = remember(isDarkMode) {
        Markwon.builder(context)
            .usePlugin(HtmlPlugin.create())
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(LinkifyPlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(context))
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    if (isDarkMode) {
                        builder
                            .codeTextColor(Color.WHITE)
                            .codeBackgroundColor(Color.BLACK)
                    } else {
                        builder
                            .codeTextColor(Color.BLACK)
                            .codeBackgroundColor(Color.WHITE)
                    }

                }
            })
            .build()
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            TextView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        },
        update = { textView ->
            if (isDarkMode) {
                textView.setTextColor(Color.WHITE)
            } else {
                textView.setTextColor(Color.BLACK)
            }
            markwon.setMarkdown(textView, markdownContent)
        }
    )
}