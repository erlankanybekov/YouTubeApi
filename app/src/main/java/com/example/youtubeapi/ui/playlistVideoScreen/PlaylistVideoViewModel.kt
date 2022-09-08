package com.example.youtubeapi.ui.playlistVideoScreen

import androidx.lifecycle.LiveData
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.models.Item
import com.example.youtubeapi.repository.Repository
class PlaylistVideoViewModel: BaseViewModel() {

    private var repository= Repository()

    fun getPlaylistItems(playlistId: String):LiveData<Resource<Item>>{
        return repository.getPlaylistItems(playlistId)
    }

}