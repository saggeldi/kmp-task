package com.shageldi.githubrepo.core.di

import com.shageldi.githubrepo.core.network.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { httpClient(get()) }
}