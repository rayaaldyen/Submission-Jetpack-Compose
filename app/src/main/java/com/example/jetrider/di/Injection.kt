package com.example.jetrider.di

import com.example.jetrider.data.RiderRepository

object Injection {
    fun provideRepository(): RiderRepository {
        return RiderRepository.getInstance()
    }
}