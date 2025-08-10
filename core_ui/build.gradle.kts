import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.shageldi.core_ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }




    buildFeatures {
        compose = true
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }




}


configurations.all {
    resolutionStrategy {
        force("org.jetbrains:annotations:23.0.0")
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.coil)
    implementation(libs.coil.compose)




    implementation(libs.markwon.core) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.ext.latex) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.ext.strikethrough) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.ext.tables) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.ext.tasklist) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.html) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.image) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.image.glide) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }

    implementation(libs.markwon.inline.parser) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.linkify) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.simple.ext) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    implementation(libs.markwon.syntax.highlight) {
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
    debugImplementation(libs.ui.tooling)
}