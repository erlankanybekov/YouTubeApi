package com.example.youtubeapi.data.remote

import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.data.remote.models.Playlists
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
 suspend  fun getPlaylists(
        @Query("channelId") channelId: String,
        @Query("part") part : String,
        @Query("key")apiKey:String,
        @Query("maxResults") maxResult: Int
    ):Response<Playlists>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResult: Int
    ): Response<Item>
}