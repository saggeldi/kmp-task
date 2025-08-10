package com.shageldi.githubrepo.di

import com.shageldi.githubrepo.feature.github.viewmodel.GithubViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val githubModule = module {
    viewModelOf(::GithubViewModel)
}