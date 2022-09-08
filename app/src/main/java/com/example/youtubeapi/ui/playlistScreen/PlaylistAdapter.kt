package com.example.youtubeapi.ui.playlistScreen


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.ItemPlaylistsRvBinding
import com.example.youtubeapi.extensions.Glide
import com.example.youtubeapi.data.models.Item

import kotlin.collections.ArrayList



class PlaylistAdapter(
    private var data: ArrayList<Item>,
    private val onItemClick: (item: String, String, String) -> Unit?
) : RecyclerView.Adapter<PlaylistAdapter.PlayListsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsViewHolder {
        val binding =
            ItemPlaylistsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayListsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class PlayListsViewHolder(var binding: ItemPlaylistsRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.ivVideos.Glide(item.snippet.thumbnails.default.url)
            binding.tvName.text = item.snippet.title
            binding.tvDesc.text =String.format("${item.contentDetails.itemCount} ${itemView.context.getString(
                R.string.Playlist_video_series)}")
            binding.root.setOnClickListener{
                onItemClick(item.snippet.title, item.snippet.description, item.id)
            }
        }
    }
}

