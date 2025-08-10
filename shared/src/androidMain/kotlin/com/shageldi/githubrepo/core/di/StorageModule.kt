package com.shageldi.githubrepo.core.di

import com.shageldi.githubrepo.core.storage.TokenStorage
import org.koin.dsl.module

val storageModule = module {
    single { TokenStorage(get()) }
}