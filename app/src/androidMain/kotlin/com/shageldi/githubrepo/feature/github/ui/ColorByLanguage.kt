package com.shageldi.githubrepo.feature.github.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.shageldi.core_ui.state.LocalDarkMode

object ProgrammingLanguageColors {

    private val languageColorMap = mapOf(
        "kotlin" to Color(0xFFA97BFF),
        "java" to Color(0xFFB07219),
        "javascript" to Color(0xFFF1E05A),
        "typescript" to Color(0xFF2B7489),
        "python" to Color(0xFF3572A5),
        "swift" to Color(0xFFFA7343),
        "dart" to Color(0xFF00B4AB),
        "go" to Color(0xFF00ADD8),
        "rust" to Color(0xFFDEA584),
        "c" to Color(0xFF555555),
        "c++" to Color(0xFFF34B7D),
        "csharp" to Color(0xFF239120),
        "php" to Color(0xFF4F5D95),
        "ruby" to Color(0xFF701516),
        "scala" to Color(0xFFC22D40),
        "r" to Color(0xFF198CE7),
        "matlab" to Color(0xFFE16737),
        "shell" to Color(0xFF89E051),
        "powershell" to Color(0xFF012456),
        "objective-c" to Color(0xFF438EFF),
        "perl" to Color(0xFF0298C3),
        "lua" to Color(0xFF000080),
        "haskell" to Color(0xFF5E5086),
        "clojure" to Color(0xFFDB5855),
        "elixir" to Color(0xFF6E4A7E),
        "erlang" to Color(0xFFB83998),
        "f#" to Color(0xFFB845FC),
        "groovy" to Color(0xFFE69F56),

        "html" to Color(0xFFE34C26),
        "css" to Color(0xFF1572B6),
        "scss" to Color(0xFFCF649A),
        "sass" to Color(0xFFA53B70),
        "less" to Color(0xFF1D365D),
        "vue" to Color(0xFF2C3E50),
        "react" to Color(0xFF61DAFB),
        "angular" to Color(0xFFDD0031),

        "json" to Color(0xFF292929),
        "yaml" to Color(0xFFCB171E),
        "xml" to Color(0xFF0060AC),
        "toml" to Color(0xFF9C4221),
        "ini" to Color(0xFFD1BBA4),

        "sql" to Color(0xFFE38C00),
        "plsql" to Color(0xFFDADAD0),

        "markdown" to Color(0xFF083FA1),
        "tex" to Color(0xFF3D6117),

        "objective-c++" to Color(0xFF6866FB),
        "flutter" to Color(0xFF02569B),

        "dockerfile" to Color(0xFF384D54),
        "makefile" to Color(0xFF427819),
        "cmake" to Color(0xFFDA3434),
        "vim script" to Color(0xFF199F4B),
        "emacs lisp" to Color(0xFFC065DB),
        "assembly" to Color(0xFF6E4C13),
        "pascal" to Color(0xFFE3F171),
        "fortran" to Color(0xFF4D41B1),
        "cobol" to Color(0xFF08007B),
    )

    private val defaultColor = Color(0xFF8A8A8A)


    fun getLanguageColor(
        language: String,
        isDarkTheme: Boolean = false,
        alpha: Float = 1.0f
    ): Color {
        val baseColor = languageColorMap[language.lowercase()] ?: defaultColor

        return if (isDarkTheme) {
            adjustColorForDarkTheme(baseColor).copy(alpha = alpha)
        } else {
            adjustColorForLightTheme(baseColor).copy(alpha = alpha)
        }
    }

    private fun adjustColorForDarkTheme(color: Color): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(
            android.graphics.Color.rgb(
                (color.red * 255).toInt(),
                (color.green * 255).toInt(),
                (color.blue * 255).toInt()
            ), hsv
        )

        hsv[1] = (hsv[1] * 1.1f).coerceAtMost(1.0f) // Saturation
        hsv[2] = (hsv[2] * 1.2f).coerceAtMost(1.0f) // Brightness

        val adjustedColor = android.graphics.Color.HSVToColor(hsv)
        return Color(adjustedColor)
    }


    private fun adjustColorForLightTheme(color: Color): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(
            android.graphics.Color.rgb(
                (color.red * 255).toInt(),
                (color.green * 255).toInt(),
                (color.blue * 255).toInt()
            ), hsv
        )

        if (hsv[2] > 0.8f) {
            hsv[2] = 0.7f
        }

        val adjustedColor = android.graphics.Color.HSVToColor(hsv)
        return Color(adjustedColor)
    }


}

@Composable
fun String.toLanguageColor(alpha: Float = 1.0f): Color {
    val isDarkTheme by LocalDarkMode.current
    return ProgrammingLanguageColors.getLanguageColor(this.lowercase(), isDarkTheme, alpha)
}