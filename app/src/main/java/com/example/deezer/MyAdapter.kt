package com.example.deezer

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
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
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    @SuppressLint("NotifyDataSetChanged")
    fun search(query: String) {
        filteredList = if (query.isNotEmpty()) {
            searchItems.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            searchItems
        }
        notifyDataSetChanged()
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private var buttonPlayer: ImageView? = null

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)
        private val icplay: ImageView = itemView.findViewById(R.id.icplay)
        private val icpause: ImageView = itemView.findViewById(R.id.icpause)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(searchItem: SearchItem) {
            titleTextView.text = searchItem.title
            artistTextView.text = searchItem.artist.name
            Glide.with(itemView).load(searchItem.album.cover).into(albumImageView)

            icplay.setOnClickListener{
                releaseMediaPlayer()

                buttonPlayer?.setImageResource(R.drawable.ic_play)
                buttonPlayer=icplay
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(searchItem.preview)
                prepareAsync()
                icplay.setImageResource(R.drawable.ic_pause)
                setOnPreparedListener {
                    start()
                }
                setOnCompletionListener {
                    icplay.setImageResource(R.drawable.ic_play)
                }
            }
        }
        }
    }
}