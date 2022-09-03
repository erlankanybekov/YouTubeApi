package com.example.youtubeapi.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.Glide(url:String){
    Glide.with(this).load(url).into(this)
}