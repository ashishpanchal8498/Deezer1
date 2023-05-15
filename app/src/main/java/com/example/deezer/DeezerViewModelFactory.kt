package com.example.deezer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deezer.reopository.DeezerRepository

class DeezerViewModelFactory(private val repository: DeezerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeezerViewModel::class.java)) {
            return DeezerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
