package com.shageldi.githubrepo.di

import com.shageldi.githubrepo.feature.auth.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::AuthViewModel)
}