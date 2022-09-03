package com.example.youtubeapi.ui.firstScreen

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.base.BaseActivity
import com.example.youtubeapi.databinding.ActivityMainBinding
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.ui.secondScreen.MainActivity2

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
     override val viewModel: MainViewModel
         get() = ViewModelProvider(this)[MainViewModel::class.java]

     override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
         return ActivityMainBinding.inflate(layoutInflater)
     }

    private fun initRecyclerView(playlistsList: List<Item>) {
        binding.recyclerView.adapter = PlaylistsAdapter(playlistsList as ArrayList<Item>, this::onItemClick)
    }

    private fun onItemClick(channelId: String, playlistTitle: String, playlistDescription: String) {
        Intent(this, MainActivity2::class.java).apply {
            putExtra(FIRST_KEY, channelId)
            putExtra(SECOND_KEY, playlistTitle)
            putExtra(THIRD_KEY, playlistDescription)
            startActivity(this)
        }
    }
    companion object {
        const val FIRST_KEY = "one_key"
        const val SECOND_KEY = "two_key"
        const val THIRD_KEY = "third_key"

    }

    override fun initViewModel() {
         super.initViewModel()
         viewModel.playlists().observe(this,{
             Toast.makeText(this,it.kind,Toast.LENGTH_SHORT).show()
             initRecyclerView(it.items)
         })

     }

 }