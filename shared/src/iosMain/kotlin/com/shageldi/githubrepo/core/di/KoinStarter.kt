package com.shageldi.githubrepo.core.di

import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules(networkModule)
    }
}