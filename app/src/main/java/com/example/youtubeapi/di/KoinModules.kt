package com.example.youtubeapi.di

import com.example.youtubeapi.core.network.networkModule
import com.example.youtubeapi.data.local.prefsModule

val koinModules = listOf(
    repoModules,
    viewModules,
    networkModule,
    prefsModule
)