package com.shageldi.githubrepo

import android.app.Application
import com.shageldi.githubrepo.core.di.initKoinAndroid
import com.shageldi.githubrepo.di.authModule
import com.shageldi.githubrepo.di.githubModule

class GithubRepoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(
            this, listOf(
                authModule,
                githubModule
            )
        )
    }
}