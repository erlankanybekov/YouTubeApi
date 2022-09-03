package com.example.youtubeapi.remote

import com.example.youtubeapi.models.Playlists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylists(
        @Query("channelId") channelId: String,
        @Query("part") part : String,
        @Query("key")apiKey:String
    ):Call<Playlists>
}