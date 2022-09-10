package com.example.youtubeapi.ui.playlistVideoScreen

import androidx.lifecycle.LiveData
import com.example.youtubeapi.App.Companion.repository
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.remote.models.Item

class PlaylistVideoViewModel: BaseViewModel() {

    fun getPlaylistItems(playlistId: String):LiveData<Resource<Item>>{
        return repository.getPlaylistItems(playlistId)
    }

}