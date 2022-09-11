package com.example.youtubeapi.ui.videoPlayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.BuildConfig
import com.example.youtubeapi.R
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.local.Prefs
import com.example.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.example.youtubeapi.ui.playlistVideoScreen.PlaylistVideoActivity
import com.example.youtubeapi.utils.CheckConnectNetwork
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerActivity : BaseActivity<VideoPlayerViewModel,ActivityVideoPlayerBinding>(),Player.Listener {

    private var videoId: String? = null
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override val viewModel: VideoPlayerViewModel
        get() =  ViewModelProvider(this)[VideoPlayerViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (videoId != null){
            binding.progressBar.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.VISIBLE
        }
        setupPlayer()
        addMp4Files()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(inflater)
    }
    private fun setupPlayer(){
        player = ExoPlayer.Builder(this).build()
        playerView = binding.videoView
        playerView.player = player
        player.addListener(this)
    }
    private fun addMp4Files(){
        val mediaItem = MediaItem.fromUri("https://rr8---sn-n8v7znzl.googlevideo.com/videoplayback?expire=1662899146&ei=an8dY6WKIJHFyQWvpbDYBg&ip=194.32.122.53&id=o-ABJjcOHSq0uZg1toa9Qs3a5z8XFBrGgIyptxrSjzFg2P&itag=18&source=youtube&requiressl=yes&spc=lT-Khqnwj_8v-3FywwwFjFpPH6jnTUA&vprv=1&mime=video%2Fmp4&ns=j647tl4lxiSE1PH6dCQ6_eAH&gir=yes&clen=15912045&ratebypass=yes&dur=273.832&lmt=1661996688603978&fexp=24001373,24007246&c=WEB&rbqsm=fr&txp=5538434&n=DGuZ6Am88bybyg&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAOHQUZ_NdbSJhTul4CKjxMQ2zmLcKUKKa3VUJeUBNxCsAiBuvrwrjkkqSRrzQRHdzqqGXZaDgUnPlh44o4z6W5qV4g%3D%3D&rm=sn-cpux-8ovs7e,sn-f5fes7l&req_id=bc108e4fde33a3ee&redirect_counter=2&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=au&mip=158.181.181.183&mm=29&mn=sn-n8v7znzl&ms=rdu&mt=1662877497&mv=m&mvi=8&pl=20&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIhAOsltL0z57c1FSLTI3AZ1D-RssiSkh7bGt0BXTKOtoDmAiADHLV4PGMRaIrB7a0Svzq9XpWdKywaMJISKe7MM66gUg%3D%3D")
        player.addMediaItem(mediaItem)
        player.prepare()
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when(playbackState){
            Player.STATE_BUFFERING->{
                binding.videoProgressBar.visibility = View.VISIBLE
            }
            Player.STATE_READY->{
                binding.videoProgressBar.visibility = View.GONE
            }
            Player.STATE_ENDED -> {

            }
            Player.STATE_IDLE -> {

            }
        }
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        binding.videoTitle.text = mediaMetadata.title?:mediaMetadata.displayTitle?:"title not found"
    }


    override fun initView() {
        getDataIntent()
        binding.noInet.rlParent.visibility = View.GONE
        binding.downloadLayout.visibility = View.INVISIBLE
    }


    private fun getDataIntent() {
        videoId = intent.getStringExtra(PlaylistVideoActivity.idPdaVa).toString()
        binding.videoTitle.text = intent.getStringExtra(PlaylistVideoActivity.titlePdaVa).toString()
        binding.videoDesc.text = intent.getStringExtra(PlaylistVideoActivity.descPdaVa)?.toString()
    }


    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
    override fun initListener() {
        binding.tvBack.setOnClickListener{
            onBackPressed()
        }
    }

    override fun initViewModel() {
        binding.progressBar.isVisible = true

    }

    override fun checkInternet() {
        CheckConnectNetwork(this).observe(this, { isConnected ->
            binding.noInet.rlParent.isVisible = !isConnected
        })
    }



}



