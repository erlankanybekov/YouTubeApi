package com.example.youtubeapi.ui.secondScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.R
import com.example.youtubeapi.base.BaseActivity
import com.example.youtubeapi.databinding.ActivityMain2Binding
import com.example.youtubeapi.ui.firstScreen.MainActivity
import com.example.youtubeapi.ui.firstScreen.MainViewModel

class MainActivity2 : BaseActivity<MainViewModel,ActivityMain2Binding>() {
    private var playlistId: String? = null
    override val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMain2Binding {
        return ActivityMain2Binding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        playlistId = intent.getStringExtra(MainActivity.FIRST_KEY).toString()
        Toast.makeText(this, playlistId, Toast.LENGTH_SHORT).show()

    }


}