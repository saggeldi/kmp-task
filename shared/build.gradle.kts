import com.android.build.api.dsl.VariantDimension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}




kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }


    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)


            // KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

inline fun <reified ValueT> VariantDimension.buildConfigField(name: String, value: ValueT) {
    val resolvedValue = when (value) {
        is String -> "\"$value\""
        is Boolean -> "\"Boolean.parseBoolean(\"$value\")\""
        else -> value.toString()
    }

    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}

android {
    namespace = "com.shageldi.githubrepo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        val configProperties = Properties()
        val configPropsFile = file("config/app.cfg")

        if (configPropsFile.exists()) {
            configPropsFile.inputStream().use {
                configProperties.load(it)
            }
            buildConfigField("GH_API_VERSION",configProperties.getProperty("GH_API_VERSION"))
        } else {
            buildConfigField("GH_API_VERSION",System.getenv("GH_API_VERSION"))
        }
    }

    lint {
        disable.add("NullSafeMutableLiveData")
    }

    buildFeatures {
        buildConfig = true
    }
}
