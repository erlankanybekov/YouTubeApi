package com.example.youtubeapi.ui.playlistVideoScreen

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.databinding.PlaylistVideoActivityBinding
import com.example.youtubeapi.ui.playlistScreen.PlaylistActivity
import com.example.youtubeapi.ui.videoPlayer.VideoPlayerActivity
import com.example.youtubeapi.utils.CheckConnectNetwork

class PlaylistVideoActivity : BaseActivity<PlaylistVideoViewModel, PlaylistVideoActivityBinding>() {
    private var playlistId: String? = null
    override val viewModel:PlaylistVideoViewModel
        get() = ViewModelProvider(this)[PlaylistVideoViewModel::class.java]

    override fun inflateViewBinding(inflater: LayoutInflater): PlaylistVideoActivityBinding {
        return PlaylistVideoActivityBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        playlistId = intent.getStringExtra(PlaylistActivity.FIRST_KEY).toString()
        binding.playlistTitle.text = intent.getStringExtra(PlaylistActivity.SECOND_KEY).toString()
        binding.playlistDescription.text = intent.getStringExtra(PlaylistActivity.THIRD_KEY).toString()

        initVM()
    }

    override fun initViewModel() {
        super.initViewModel()


    }

    private fun initVM() {
        playlistId?.let {
            viewModel.getPlaylistItems(it).observe(this) {
                when(it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {

                            initRecyclerView(it.data.items as ArrayList<Item>)
                            binding.progressBar.isVisible = false
                            binding.seriesTv.text = it.data.items.size.toString() + " video series"
                        } else {
                            Log.e("Error1", "error 1")
                        }
                    }
                    Status.ERROR -> {
                        Log.e("Error2", "error 2")
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }
    private fun initRecyclerView(playlistsList: ArrayList<Item>?) {
        binding.videosRecyclerView.adapter = PlaylistsVideoAdapter(playlistsList!!, this::onItemClick)
        Log.e("ololo",playlistId.toString())
        binding.videosRecyclerView.visibility = View.VISIBLE
    }
    private fun onItemClick(videoId: String?, videoTitle: String?, videoDesc: String?) {
        Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra(idPdaVa, videoId)
            putExtra(titlePdaVa, videoTitle)
            putExtra(descPdaVa, videoDesc)

            startActivity(this)
        }
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
        binding.fab.setOnClickListener {
            onItemClick(idPdaVa, titlePdaVa, descPdaVa)
        }
    }
    companion object {
        const val idPdaVa = "idPdaVa"
        const val titlePdaVa = "titlePdaVa"
        const val descPdaVa = "descPdaVa"
    }


}