package com.example.deezer.reopository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.deezer.ApiInterface
import com.example.deezer.paging.DeezerPagingSource

class DeezerRepository constructor(private val apiInterface: ApiInterface) {

    fun getData(string: String) = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { DeezerPagingSource(apiInterface,string) }
    ).flow
}