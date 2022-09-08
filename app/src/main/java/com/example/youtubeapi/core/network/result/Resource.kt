package com.example.youtubeapi.core.network.result

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code: Int?){
    companion object{
        fun <T> success(data:T?,code:Int?= null):Resource<T>{
            return Resource(Status.SUCCESS,data,null,code)
        }
        fun <T> error(data:T?,msg:String?,code:Int?= null):Resource<T>{
            return Resource(Status.ERROR,data,msg,code)
        }
        fun <T> loading(data:T?):Resource<T>{
            return Resource(Status.LOADING,data,null,null)
        }
    }
}