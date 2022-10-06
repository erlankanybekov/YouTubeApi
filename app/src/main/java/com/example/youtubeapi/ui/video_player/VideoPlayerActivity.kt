package com.example.youtubeapi.ui.video_player

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.youtubeapi.BuildConfig
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.local.Prefs
import com.example.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.example.youtubeapi.ui.playlist_video_screen.PlaylistVideoActivity
import com.example.youtubeapi.utils.CheckConnectNetwork
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import okio.`-DeprecatedOkio`.source
import org.koin.android.ext.android.inject

class VideoPlayerActivity : BaseActivity<VideoPlayerViewModel,ActivityVideoPlayerBinding>(),Player.Listener {

    private var videoId: String? = null
    private lateinit var player: ExoPlayer
    private lateinit var videoSource: ProgressiveMediaSource
    private lateinit var audioSource: ProgressiveMediaSource
    private val prefs: Prefs by inject()

    override val viewModel: VideoPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("SeekTime", player.currentPosition)
        outState.putInt("mediaItem", player.currentMediaItemIndex)
    }

    fun initListener(savedInstanceState: Bundle?) {
        super.initListener()
        Log.d("prefs", prefs.onBoard.toString())
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
        downloadVideo(savedInstanceState)
        binding.downloadButton.setOnClickListener {
            getYoutubeDownloadUrl(BuildConfig.YOUTUBE_BASE + videoId)
        }

    }

    private fun downloadVideo(savedInstanceState: Bundle?) {
        Log.e("Video", "${BuildConfig.YOUTUBE_BASE + videoId}")

        object : YouTubeExtractor(this) {
            @SuppressLint("StaticFieldLeak")
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                if (ytFiles != null) {
                    val videoTag = 133
                    val audioTag = 140
                    val videoUrl = ytFiles[videoTag].url
                    val audioUrl = ytFiles[audioTag].url
                    setupPlayer(videoUrl, audioUrl)
                    savedInstanceState?.getInt("mediaItem")?.let { restoredMedia ->
                        val seekTime = savedInstanceState.getLong("SeekTime")
                        player.seekTo(restoredMedia, seekTime)
                        player.play()
                    }
                }
            }
        }.extract(BuildConfig.YOUTUBE_BASE + videoId,false,true)
    }

    override fun initView() {
        getDataIntent()
        binding.noInet.root.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.downloadLayout.visibility = View.INVISIBLE
    }

    private fun getDataIntent() {
        videoId = intent.getStringExtra(PlaylistVideoActivity.idPdaVa).toString()
        binding.videoTitle.text = intent.getStringExtra(PlaylistVideoActivity.titlePdaVa).toString()
        binding.videoDesc.text = intent.getStringExtra(PlaylistVideoActivity.descPdaVa).toString()
    }

    private fun setupPlayer(videoUrl: String, audioUrl: String) {
        buildMediaSource(videoUrl, audioUrl)
        player = ExoPlayer.Builder(this).build()
        binding.videoView.player = player
        player.setMediaSource(MergingMediaSource(videoSource, audioSource))
        player.addListener(this)
        player.prepare()
    }

    private fun buildMediaSource(videoUrl: String, audioUrl: String) {
        videoSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(videoUrl))

        audioSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(audioUrl))
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when (playbackState) {
            Player.STATE_BUFFERING -> binding.videoProgressBar.visibility = View.VISIBLE
            Player.STATE_READY -> binding.videoProgressBar.visibility = View.INVISIBLE
            Player.STATE_ENDED -> {}
            Player.STATE_IDLE -> {}
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun initViewModel() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
    }

    override fun checkInternet() {
        super.checkInternet()
        CheckConnectNetwork(this).observe(this, { isConnected ->
            binding.noInet.rlParent.isVisible = !isConnected
            binding.root.isVisible = isConnected
        })
    }

    private fun getYoutubeDownloadUrl(youtubeLink: String?) {
        object : YouTubeExtractor(this) {

            public override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                vMeta: VideoMeta?
            ) {
                if (ytFiles == null) {
                    Toast.makeText(this@VideoPlayerActivity, "Error", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }
                var i = 0
                var itag: Int
                while (i < ytFiles.size()) {
                    itag = ytFiles.keyAt(i)
                    val ytFile = ytFiles[itag]

                    if (ytFile.format.height == -1 || ytFile.format.height >= 360) {
                        Log.e("itag is ", itag.toString())
                        addButtonToMainLayout(vMeta!!.title, ytFile)
                    }
                    i++
                }
            }
        }.extract(youtubeLink,false,true)
    }

    private fun addButtonToMainLayout(videoTitle: String, ytfile: YtFile) {
        var btnText = if (ytfile.format.height == -1) "Audio " +
                ytfile.format.audioBitrate + " kbit/s" else ytfile.format.height.toString() + "p"
        btnText += if (ytfile.format.isDashContainer) " dash" else ""
        val btn = RadioButton(this)
//        radio.text = btnText
        btn.text = btnText
        btn.setOnClickListener {
            var filename: String = if (videoTitle.length > 55) {
                videoTitle.substring(0, 55) + "." + ytfile.format.ext
            } else {
                videoTitle + "." + ytfile.format.ext
            }
            filename = filename.replace("[\\\\><\"|*?%:#/]".toRegex(), "")
            downloadFromUrl(ytfile.url, videoTitle, filename)
            //показать описание и скрыть выбор при выборе
            binding.mainDetails.visibility = View.VISIBLE
            binding.downloadLayout.visibility = View.INVISIBLE
            finish()
        }
        binding.downloadLayout!!.addView(btn)
        binding.mainDetails.visibility = View.GONE
        binding.downloadLayout.visibility = View.VISIBLE
        binding.cancelDownloading.setOnClickListener {
            binding.mainDetails.visibility = View.VISIBLE
            binding.downloadLayout.visibility = View.INVISIBLE
        }
    }

    private fun downloadFromUrl(youtubeDlUrl: String, downloadTitle: String, fileName: String) {
        val uri = Uri.parse(youtubeDlUrl)
        val request = DownloadManager.Request(uri)
        request.setTitle(downloadTitle)
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}


