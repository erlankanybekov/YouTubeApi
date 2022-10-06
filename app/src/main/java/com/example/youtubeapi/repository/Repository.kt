package com.example.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.youtubeapi.BuildConfig
import com.example.youtubeapi.data.remote.models.Playlists
import com.example.youtubeapi.data.remote.ApiService
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.data.remote.models.RemoteDataSource
import com.example.youtubeapi.utils.Const
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val dataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<Resource<Playlists>> = liveData (Dispatchers.IO){
        emit(Resource.loading())
        val response = dataSource.getPlaylists()
        emit(response)
 //       val data = MutableLiveData<Resource<Playlists>>()
//        data.value = Resource.loading(null)
//        apiService.getPlaylists(Const.channelId, Const.part, BuildConfig.API_KEY, Const.maxResult)
//            .enqueue(object :
//                Callback<Playlists> {
//                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
//                    if (response.isSuccessful) {
//                        data.value = Resource.success(response.body())
//                    }
//                }
//                override fun onFailure(call: Call<Playlists>, t: Throwable) {
//                    data.value = Resource.error(null, t.message, null)
//                }
//
//            })
     //   return data
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<Item>> = liveData(Dispatchers.IO) {

        emit(Resource.loading())
        val response = dataSource.getPlaylistItems(playlistId)
        emit(response)
//        val data = MutableLiveData<Resource<Item>>()
//        data.value = Resource.loading(null)
//        apiService.getPlaylistItems(Const.part, playlistId, BuildConfig.API_KEY, Const.maxResult)
//            .enqueue(object : Callback<Item> {
//                override fun onResponse(call: Call<Item>, response: Response<Item>) {
//                    if (response.isSuccessful) {
//                        data.value = Resource.success(response.body())
//                    }
//                }
//
//                override fun onFailure(call: Call<Item>, t: Throwable) {
//                    data.value = Resource.error(null, t.message, null)
//                }
//
//            })
//        return data
    }

}