package com.example.youtubeapi.data.local

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val prefsModule:Module= module {
    single { Prefs(androidContext()) }
}
class Prefs(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs",Context.MODE_PRIVATE)

    var onBoard:Boolean
        get()= prefs.getBoolean("onBoard",false)

        set(value) {prefs.edit().putBoolean("onBoard",value).apply()}
}