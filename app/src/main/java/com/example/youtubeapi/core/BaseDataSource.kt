package com.example.youtubeapi.core

import com.example.youtubeapi.core.network.result.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                if (body!=null) return Resource.success(body)
            } else {
                return Resource.error(response.body(), response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null,429)
        }
        return Resource.error(null, null,429)
    }
}