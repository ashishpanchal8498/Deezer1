package com.example.deezer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val searchItems: List<SearchItem>) : RecyclerView.Adapter<MyAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = searchItems[position]
        holder.bind(searchItem)
    }

    override fun getItemCount(): Int = searchItems.size

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)

            fun bind(searchItem: SearchItem) {
                titleTextView.text = searchItem.title
                artistTextView.text = searchItem.artist.toString()
                Glide.with(itemView)
                    .load(searchItem.album.cover)
                    .into(albumImageView)
        }
    }
}
