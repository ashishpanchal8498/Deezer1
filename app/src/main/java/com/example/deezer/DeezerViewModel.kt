package com.example.deezer

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.deezer.reopository.DeezerRepository
import kotlinx.coroutines.flow.Flow

class DeezerViewModel constructor(val repository: DeezerRepository) : ViewModel() {
    fun returnList(string:String): Flow<PagingData<SearchItem>> {
        return repository.getData(string)
    }
}