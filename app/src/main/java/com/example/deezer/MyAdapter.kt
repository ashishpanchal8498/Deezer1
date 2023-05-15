package com.example.deezer

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(
    diff: DiffUtil.ItemCallback<SearchItem>) :
    PagingDataAdapter<SearchItem, MyAdapter.SearchViewHolder>(diff) {
    var mediaPlayer: MediaPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
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

            icplay.setOnClickListener {
                releaseMediaPlayer()

                buttonPlayer?.setImageResource(R.drawable.ic_play)
                buttonPlayer = icplay
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