package com.example.jetrider.data

import com.example.jetrider.model.RiderData
import com.example.jetrider.model.Riders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RiderRepository {

    private val riders = mutableListOf<Riders>()

    fun getRiders(): Flow<List<Riders>> {
        return flowOf(riders)
    }

    init {
        if (riders.isEmpty()) {
            RiderData.riders.forEach {
                riders.add(Riders(it, false))
            }
        }
    }

    fun searchRiders(query: String): Flow<List<Riders>> {
        return flow {
            emit(riders.filter {
                it.rider.name.contains(query, ignoreCase = true)
            })
        }

    }

    fun getRiderById(riderId: String): Riders {
        return riders.first {
            it.rider.id == riderId
        }
    }

    fun updateFavoriteRider(riderId: String, isFavorite: Boolean): Flow<Boolean> {
        val index = riders.indexOfFirst { it.rider.id == riderId }
        val result = if (index > -1) {
            val rider = riders[index]
            riders[index] =
                rider.copy(isFavorite = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoriteRider(): Flow<List<Riders>> {
        return getRiders()
            .map { riders ->
                riders.filter { rider ->
                    rider.isFavorite
                }
            }
    }

    companion object {
        @Volatile
        private var instance: RiderRepository? = null

        fun getInstance(): RiderRepository =
            instance ?: synchronized(this) {
                RiderRepository().apply {
                    instance = this
                }
            }
    }
}