package com.example.deezer


data class SearchResult(
    val data: List<SearchItem>,
    val total: Int,
    val next: String?=null
)

data class SearchItem(
    val id: Long,
    val title: String,
    val link: String,
    val duration: Int,
    val preview: String,
    val artist: SearchArtist,
    val album: SearchAlbum,
    val response: SearchResponse,
    val type: String
)

data class SearchArtist(
    val id: Long,
    val name: String,
    val picture: String
)

data class SearchAlbum(
    val id: Long,
    val title: String,
    val cover: String
)

data class SearchResponse(
    val data: List<SearchItem>,
    val total: Int,
    val next: String?,
    val prev: String?
)


