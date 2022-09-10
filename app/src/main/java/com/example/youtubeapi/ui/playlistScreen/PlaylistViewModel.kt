package com.example.youtubeapi.ui.playlistScreen

import androidx.lifecycle.LiveData
import com.example.youtubeapi.App.Companion.repository
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.remote.models.Playlists

class PlaylistViewModel: BaseViewModel() {


    fun getPlaylists():LiveData<Resource<Playlists>>{
        return repository.getPlaylists()
    }


}