package com.fathoor.cityzen.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fathoor.cityzen.data.di.Injection.provideRepository
import com.fathoor.cityzen.presentation.screen.detail.DetailViewModel
import com.fathoor.cityzen.presentation.screen.highlight.HighlightViewModel
import com.fathoor.cityzen.presentation.screen.home.HomeViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(provideRepository())
            DetailViewModel::class.java -> DetailViewModel(provideRepository())
            HighlightViewModel::class.java -> HighlightViewModel(provideRepository())
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
}