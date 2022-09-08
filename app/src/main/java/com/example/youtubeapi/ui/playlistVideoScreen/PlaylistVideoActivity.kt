package com.example.youtubeapi.ui.playlistVideoScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.models.Item
import com.example.youtubeapi.databinding.PlaylistVideoActivityBinding
import com.example.youtubeapi.ui.playlistScreen.PlaylistActivity
import com.example.youtubeapi.utils.CheckConnectNetwork

class PlaylistVideoActivity : BaseActivity<PlaylistVideoViewModel, PlaylistVideoActivityBinding>() {
    private var playlistId: String? = null
    override val viewModel:PlaylistVideoViewModel
        get() = ViewModelProvider(this)[PlaylistVideoViewModel::class.java]

    override fun inflateViewBinding(inflater: LayoutInflater): PlaylistVideoActivityBinding {
        return PlaylistVideoActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        playlistId = intent.getStringExtra(PlaylistActivity.FIRST_KEY).toString()
        binding.playlistTitle.text = intent.getStringExtra(PlaylistActivity.SECOND_KEY).toString()
        binding.playlistDescription.text = intent.getStringExtra(PlaylistActivity.THIRD_KEY).toString()

    }

    override fun initViewModel() {
        super.initViewModel()
        initVM()

    }
    @SuppressLint("SetTextI18n")
    private fun initVM() {
        playlistId?.let {
            viewModel.getPlaylistItems(it).observe(this) {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { it1 -> initRecyclerView(it1.items) }
                            binding.seriesTv.text = it.data?.items?.size.toString() + " video series"
                        }
                    Status.ERROR ->{
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = true
                    }
                    Status.LOADING ->{
                        binding.progressBar.isVisible = false
                    }
                    }
                }
            }
        }
    private fun initRecyclerView(playlistsList: List<Item>) {
        binding.videosRecyclerView.adapter = PlaylistsVideoAdapter(playlistsList as ArrayList<Item>)
        binding.videosRecyclerView.visibility = View.VISIBLE
    }
    override fun checkInternet() {
        super.checkInternet()
        CheckConnectNetwork(this).observe(this, { isConnected ->
            binding.includeNoInet.rlParent.isVisible = !isConnected
            binding.videosRecyclerView.isVisible = isConnected
        })
    }
    override fun initListener() {
        binding.tvBack.setOnClickListener{
            onBackPressed()
        }
    }


}