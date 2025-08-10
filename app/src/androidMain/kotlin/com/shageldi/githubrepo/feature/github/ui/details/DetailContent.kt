package com.shageldi.githubrepo.feature.github.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shageldi.core_ui.component.GithubReadme
import com.shageldi.core_ui.component.SafeContent
import com.shageldi.core_ui.state.LocalDarkMode
import com.shageldi.core_ui.theme.AppTheme
import com.shageldi.githubrepo.R
import com.shageldi.githubrepo.core.feature.github.domain.model.SingleRepoModel

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    item: SingleRepoModel
) {
    val isDarkTheme by LocalDarkMode.current

    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (item.hasSiteUrl()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).padding(2.dp).clickable {
                        uriHandler.openUri(item.siteUrl)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_link_24),
                        contentDescription = stringResource(R.string.content_description_link),
                        tint = MaterialTheme.colorScheme.tertiary,
                    )

                    Text(
                        text = item.siteUrl,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.weight(1f)
                    )

                }
            }
        }

        if (item.hasLicense()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable {
                        uriHandler.openUri(item.licenseUrl)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_license_24),
                        contentDescription = stringResource(R.string.content_description_license),
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = stringResource(R.string.license),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500
                        )
                    )

                    Text(
                        text = item.license,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500
                        ),
                        textAlign = TextAlign.End
                    )


                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CountText(
                    count = item.starsCount,
                    text = stringResource(R.string.stars),
                    icon = painterResource(R.drawable.outline_star_24),
                    iconColor = Color.Yellow,
                    modifier = Modifier.weight(1f)
                )

                CountText(
                    count = item.forksCount,
                    text = stringResource(R.string.forks),
                    icon = painterResource(R.drawable.fork),
                    iconColor = Color.Green,
                    modifier = Modifier.weight(1f)
                )
                CountText(
                    count = item.watchersCount,
                    text = stringResource(R.string.watchers),
                    icon = painterResource(R.drawable.baseline_remove_red_eye_24),
                    iconColor = Color.Cyan,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (item.hasMarkdown()) {
            item {
                GithubReadme(
                    modifier = Modifier.fillMaxSize(),
                    markdownContent = item.markdown,
                    isDarkMode = isDarkTheme
                )
            }
        }
    }
}

@Composable
fun CountText(
    modifier: Modifier = Modifier,
    count: Int,
    text: String,
    icon: Painter,
    iconColor: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = count.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = iconColor,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun DetailContentPreview() {
    AppTheme(darkTheme = true) {
        SafeContent {
            DetailContent(
                item = SingleRepoModel(
                    id = 1,
                    fullName = "test",
                    title = "test",
                    description = "test",
                    language = "Kotlin",
                    license = "MIT",
                    licenseUrl = "https://api.github.com/licenses/mit",
                    starsCount = 1,
                    watchersCount = 3,
                    forksCount = 5,
                    siteUrl = "https://github.com/test/test",
                    markdown = """
                    # Test
                """.trimIndent()
                )
            )
        }
    }
}