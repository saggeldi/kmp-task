package com.shageldi.githubrepo.core.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.module.Module

fun initKoinAndroid(context: Context, modules: List<Module>) {
    initKoin {
        androidLogger()
        androidContext(context)
        modules(
            modules.plus(
                listOf(
                    storageModule
                )
            )
        )
    }
}