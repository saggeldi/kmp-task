package com.shageldi.githubrepo.core.di

import com.shageldi.githubrepo.core.feature.auth.data.AuthRepositoryImpl
import com.shageldi.githubrepo.core.feature.auth.domain.AuthRepository
import com.shageldi.githubrepo.core.feature.auth.domain.AuthUseCase
import org.koin.dsl.module

val authSharedModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthUseCase(get()) }
}