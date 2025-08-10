package com.shageldi.githubrepo.core.network

import android.annotation.SuppressLint
import android.util.Log
import com.shageldi.githubrepo.core.constant.Constant
import com.shageldi.githubrepo.core.storage.TokenStorage
import com.shageldi.githubrepo.shared.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


fun getUnsafeClient(): OkHttpClient {
    val trustAllCerts: Array<TrustManager> = arrayOf(
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    )
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    okHttpClientBuilder.hostnameVerifier { _, _ -> true }
    return okHttpClientBuilder.build()
}



actual fun httpClient(tokenStorage: TokenStorage): HttpClient = HttpClient(OkHttp) {
    install(HttpTimeout) {
        requestTimeoutMillis = 1000000L
        socketTimeoutMillis = 1000000L
        connectTimeoutMillis = 1000000L

    }

    engine {
        preconfigured = getUnsafeClient()
    }

    followRedirects = true
    install(HttpRedirect) {
        checkHttpMethod = false
    }



    defaultRequest {
        header("Content-Type", "application/json")
        header("Accept", "application/vnd.github+json")
        header("X-Device-Name", android.os.Build.DEVICE)
        header("X-Device-Model", android.os.Build.MODEL)
        header("X-Device-Firmware", "ANDROID SDK " + android.os.Build.VERSION.SDK_INT)
        header("X-GitHub-Api-Version", BuildConfig.GH_API_VERSION)
        url(Constant.BASE_URL)

        if (tokenStorage.getToken() != null) {
            header("Authorization","Bearer ${tokenStorage.getToken()}")
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = object: Logger {
            override fun log(message: String) {
                Log.d("HTTP Client", message)
            }
        }
        level = LogLevel.HEADERS
    }
}