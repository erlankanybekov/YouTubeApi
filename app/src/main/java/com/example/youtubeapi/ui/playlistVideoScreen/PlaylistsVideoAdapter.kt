package com.example.youtubeapi.ui.playlistVideoScreen
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeapi.R
import com.example.youtubeapi.data.remote.models.Item
import com.example.youtubeapi.databinding.ItemPlaylistVideoRvBinding
import com.example.youtubeapi.extensions.loadImage
import kotlin.collections.ArrayList



class PlaylistsVideoAdapter(
    private val list: ArrayList<Item>,
    private val onItemClick: (itemsId: String, String, String) -> Unit?
) : RecyclerView.Adapter<PlaylistsVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistVideoRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemPlaylistVideoRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(items: Item?) {
                binding.imageEv.loadImage(items?.snippet!!.thumbnails.default.url)

            binding.playlistNameTv.text = items.snippet.title
            binding.timeTv.text = items.snippet.publishedAt.dropLast(10)

            itemView.setOnClickListener {
                onItemClick( items?.snippet!!.videoId, items.snippet.title,items.snippet.description)
            }
        }
    }
}