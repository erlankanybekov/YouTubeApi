package com.example.youtubeapi.ui.playlistScreen

import androidx.lifecycle.LiveData
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.models.Playlists
import com.example.youtubeapi.repository.Repository

class PlaylistViewModel: BaseViewModel() {

    private var repository=Repository()

    fun getPlaylists():LiveData<Resource<Playlists>>{
        return repository.getPlaylists()
    }


}