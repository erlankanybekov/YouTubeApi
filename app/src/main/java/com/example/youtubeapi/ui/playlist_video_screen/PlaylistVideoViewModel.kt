package com.example.youtubeapi.ui.playlist_video_screen

import androidx.lifecycle.LiveData
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.repository.Repository

class PlaylistVideoViewModel(private val repository: Repository): BaseViewModel() {

    fun getPlaylistItems(playlistId: String):LiveData<Resource<Item>>{
        return repository.getPlaylistItems(playlistId)
    }
    fun getVideos(id: String):LiveData<Resource<Item>>{
        return repository.getVideos(id)
    }
}