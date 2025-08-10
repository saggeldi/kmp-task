# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn com.shageldi.core_ui.component.AppButtonKt
-dontwarn com.shageldi.core_ui.component.AppScreenStateKt
-dontwarn com.shageldi.core_ui.component.AppTopBarKt
-dontwarn com.shageldi.core_ui.component.ComponentState$Error
-dontwarn com.shageldi.core_ui.component.ComponentState$Idle
-dontwarn com.shageldi.core_ui.component.ComponentState$Loading
-dontwarn com.shageldi.core_ui.component.ComponentState
-dontwarn com.shageldi.core_ui.component.EmptyMessageKt
-dontwarn com.shageldi.core_ui.component.GithubReadmeKt
-dontwarn com.shageldi.core_ui.component.SafeContentKt
-dontwarn com.shageldi.core_ui.state.LocalDarkModeKt
-dontwarn com.shageldi.core_ui.state.LocalSnackBarKt
-dontwarn com.shageldi.core_ui.theme.ThemeKt