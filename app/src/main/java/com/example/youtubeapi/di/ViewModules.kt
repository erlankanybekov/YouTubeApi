package com.example.youtubeapi.di

import com.example.youtubeapi.ui.playlist_screen.PlaylistViewModel
import com.example.youtubeapi.ui.playlist_video_screen.PlaylistVideoViewModel
import com.example.youtubeapi.ui.video_player.VideoPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules : Module = module {
    viewModel { PlaylistViewModel(get()) }
    viewModel { PlaylistVideoViewModel(get()) }
    viewModel { VideoPlayerViewModel() }
}