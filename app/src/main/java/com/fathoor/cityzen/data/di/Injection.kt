package com.fathoor.cityzen.data.di

import com.fathoor.cityzen.data.repository.PlayerRepository

object Injection {
    fun provideRepository(): PlayerRepository {
        return PlayerRepository.getInstance()
    }
}