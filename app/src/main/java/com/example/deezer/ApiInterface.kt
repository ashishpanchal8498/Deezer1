package com.example.deezer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

        @GET("search?q=alesso")
        fun search(@Query("q") query: String): Call<SearchResult>
    }
