package com.shageldi.githubrepo.core.di

import com.shageldi.githubrepo.core.feature.github.data.GithubRepositoryImpl
import com.shageldi.githubrepo.core.feature.github.data.ReadmeApi
import com.shageldi.githubrepo.core.feature.github.domain.GithubRepository
import com.shageldi.githubrepo.core.feature.github.domain.GithubUseCase
import org.koin.dsl.module

val githubSharedModule = module {
    single { ReadmeApi(get()) }
    single<GithubRepository> { GithubRepositoryImpl(get(), get()) }
    factory { GithubUseCase(get()) }
}