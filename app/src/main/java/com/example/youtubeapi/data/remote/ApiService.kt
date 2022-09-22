package com.example.youtubeapi.data.remote

import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.data.remote.models.Playlists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
   fun getPlaylists(
        @Query("channelId") channelId: String,
        @Query("part") part : String,
        @Query("key")apiKey:String,
        @Query("maxResults") maxResult: Int
    ):Call<Playlists>

    @GET("playlistItems")
     fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResult: Int
    ): Call<Item>

    @GET("videos")
    fun getVideos(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResult: Int,
        @Query("id")id:String
    ): Call<Item>
}