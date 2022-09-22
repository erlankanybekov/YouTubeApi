package com.example.youtubeapi.ui.video_player

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast

import androidx.core.view.isVisible

import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.bumptech.glide.util.Util
import com.example.youtubeapi.BuildConfig
import com.example.youtubeapi.R
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.local.Prefs
import com.example.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.example.youtubeapi.ui.playlist_video_screen.PlaylistVideoActivity
import com.example.youtubeapi.utils.CheckConnectNetwork
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util.SDK_INT
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideoPlayerActivity : BaseActivity<VideoPlayerViewModel,ActivityVideoPlayerBinding>(),Player.Listener {

    private var videoId: String? = null
    private  var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition:Long = 0
    private val prefs: Prefs by inject()

    override val viewModel: VideoPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
        initPlayer()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.videoView.player = player

        val link = BuildConfig.YOUTUBE_BASE+videoId
        object : YouTubeExtractor(this){
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?,
            ) {
                if (ytFiles!=null){
                    val itag  = 137
                    val audioTag = 140
                    val videoUrl = ytFiles[itag].url
                    val audioUrl = ytFiles[audioTag].url

                    val audioSource:MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))

                    val videoSource:MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))

                    player!!.setMediaSource(MergingMediaSource(true,videoSource,audioSource),true)
                    player!!.prepare()
                    player!!.playWhenReady = playWhenReady
                    player!!.seekTo(currentWindow,playbackPosition)
                }
            }
        }.extract(link,false,true)
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT>=23){
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <24 || player == null){
            initPlayer()
        }
    }

    override fun onPause() {
        if (Build.VERSION.SDK_INT < 24) releasePlayer()
        super.onPause()
    }

    private fun releasePlayer() {
        if (player!=null){
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    override fun initListener() {
        Log.d("prefs", prefs.onBoard.toString())
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
        binding.downloadButton.setOnClickListener {
            showDialog("Select video quality")
        }

        binding.downloadButton.setOnClickListener {

        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        val downloadBtn = dialog.findViewById(R.id.materialButton) as Button
        downloadBtn.setOnClickListener {
//            Intent(this, SampleDownloadActivity::class.java).apply {
//                putExtra(Intent.EXTRA_TEXT, BuildConfig.YOUTUBE_BASE + videoId)
//                startAcivity(this)
//            }
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun initView() {
        getDataIntent()
        binding.noInet.rlParent.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.downloadLayout.visibility = View.INVISIBLE
    }

    private fun getDataIntent() {
        videoId = intent.getStringExtra(PlaylistVideoActivity.idPdaVa).toString()
        binding.videoTitle.text = intent.getStringExtra(PlaylistVideoActivity.titlePdaVa).toString()
        binding.videoDesc.text = intent.getStringExtra(PlaylistVideoActivity.descPdaVa).toString()
    }


    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT<24)releasePlayer()
        player = ExoPlayer.Builder(this).build()
        player!!.release()
    }

    override fun checkInternet() {
        super.checkInternet()
        CheckConnectNetwork(this).observe(this, { isConnected ->
            binding.noInet.rlParent.isVisible = !isConnected
        })
    }

}
