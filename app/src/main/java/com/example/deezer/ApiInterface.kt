package com.example.deezer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
//    @Headers("X-RapidAPI-Key", "1f41d2579cmsh5210b552b30453cp152c39jsn786c125f9af9")
//    @Headers("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
     suspend fun search(@Query("q") query: String,@Query("index") index: Int): SearchResult
}