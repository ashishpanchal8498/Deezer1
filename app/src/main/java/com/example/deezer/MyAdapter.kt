package com.example.deezer

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val context: Context, private val searchItems: List<SearchItem>) :
    RecyclerView.Adapter<MyAdapter.SearchViewHolder>() {
    private var filteredList: List<SearchItem> = searchItems
    var mediaPlayer: MediaPlayer? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SearchViewHolder(view)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(filteredList[position],context)
    }

    override fun getItemCount(): Int = filteredList.size
    fun search(query: String) {
        filteredList = if (query.isNotEmpty()) {
            searchItems.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            searchItems
        }
        notifyDataSetChanged()
    }
        inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)
        private val icplay: ImageView = itemView.findViewById(R.id.icplay)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(searchItem: SearchItem, context: Context) {
            titleTextView.text = searchItem.title
            artistTextView.text = searchItem.artist.name
            Glide.with(itemView)
                .load(searchItem.album.cover)
                .into(albumImageView)

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer?.setDataSource(searchItem.preview)
            mediaPlayer?.prepare()

            icplay.setOnClickListener {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                    icplay.setImageResource(R.drawable.ic_play)
                } else {
                    mediaPlayer?.start()
                    icplay.setImageResource(R.drawable.ic_pause)
                }
            }
        }
    }
}