package com.example.youtubeapi.data.remote.models

import com.example.youtubeapi.BuildConfig
import com.example.youtubeapi.core.BaseDataSource
import com.example.youtubeapi.data.remote.ApiService
import com.example.youtubeapi.utils.Const
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSourceModule:Module = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiService: ApiService):BaseDataSource() {

   suspend fun getPlaylists()=getResult {
        apiService.getPlaylists(Const.channelId, Const.part, BuildConfig.API_KEY, Const.maxResult)
    }

    suspend fun getPlaylistItems(playlistId:String)=getResult {
        apiService.getPlaylistItems(Const.part, playlistId, BuildConfig.API_KEY, Const.maxResult)
    }
}