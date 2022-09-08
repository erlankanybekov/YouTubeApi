package com.example.youtubeapi.ui.playlistScreen


import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.databinding.PlaylistActivityBinding

import com.example.youtubeapi.data.models.Item
import com.example.youtubeapi.ui.playlistVideoScreen.PlaylistVideoActivity
import com.example.youtubeapi.utils.CheckConnectNetwork

class PlaylistActivity : BaseActivity<PlaylistViewModel, PlaylistActivityBinding>() {
     override val viewModel: PlaylistViewModel
         get() = ViewModelProvider(this)[PlaylistViewModel::class.java]

     override fun inflateViewBinding(inflater: LayoutInflater): PlaylistActivityBinding {
         return PlaylistActivityBinding.inflate(layoutInflater)
     }

    private fun initRecyclerView(playlistsList: List<Item>) {
        binding.recyclerView.adapter = PlaylistAdapter(playlistsList as ArrayList<Item>, this::onItemClick)
    }

    private fun onItemClick(channelId: String, playlistTitle: String, playlistDescription: String) {
        Intent(this, PlaylistVideoActivity::class.java).apply {
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
         viewModel.getPlaylists().observe(this,{
             when(it.status){
                 Status.SUCCESS->{
                     it.data?.let { it1 -> initRecyclerView(it1.items) }
                 }
                 Status.ERROR->{Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                     binding.progressbar.isVisible = true}

                 Status.LOADING->{binding.progressbar.isVisible = false}
             }


         })
     }


    override fun checkInternet() {
        super.checkInternet()
        CheckConnectNetwork(this).observe(this, { isConnected ->
            binding.includeNoInet.rlParent.isVisible = !isConnected
            binding.recyclerView.isVisible = isConnected
        })
    }


 }