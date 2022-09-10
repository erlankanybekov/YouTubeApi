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
    private val prefs: Prefs by lazy { Prefs(this) }

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
        val mediaItem = MediaItem.fromUri("https://rr2---sn-4g5lzned.googlevideo.com/videoplayback?expire=1662838599&ei=55IcY4f5GI2ykAPguK0w&ip=207.204.229.60&id=o-AAQPfugA5VHUjiivwEDNQNUKT5nHujyEER2CobO-HBzq&itag=18&source=youtube&requiressl=yes&spc=lT-KhnkjiZQwsZOjOTMNNx-Dig8rJlg&vprv=1&mime=video%2Fmp4&ns=u6Wt_guY7h5xaM0PhRlMSdkH&cnr=14&ratebypass=yes&dur=142.059&lmt=1658775559202917&fexp=24001373,24007246&c=WEB&rbqsm=fr&txp=1318224&n=PkjNQrthSsUYmA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgHVFf_rO30PAt0cvTkg6m2_z8r8fmjO4Hkk8dKeHtYYwCIB2uMRL25bRJa312q6awGQF4AAs0H8vN98KHOuS_S7LY&rm=sn-p5qees7z&req_id=7ee5a21c757fa3ee&cm2rm=sn-hxb5apox-4g0s7e,sn-n8vrdes&ipbypass=yes&redirect_counter=3&cms_redirect=yes&cmsv=e&mh=72&mip=158.181.181.183&mm=34&mn=sn-4g5lzned&ms=ltu&mt=1662816759&mv=m&mvi=2&pl=20&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgS1cInPITJdpAr56TcPzIlQvpdJnytchhRwfj7N6KbxYCIGRiLfTZpI5XZR0Ga0nbwtsnXo8M_Esj_zhG9XB99vWg")
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



