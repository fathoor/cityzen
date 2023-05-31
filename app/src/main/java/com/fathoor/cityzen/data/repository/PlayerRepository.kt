package com.fathoor.cityzen.data.repository

import com.fathoor.cityzen.data.local.FakePlayerDataSource
import com.fathoor.cityzen.data.local.PlayerHighlight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PlayerRepository {
    private val playerHighlight = mutableListOf<PlayerHighlight>()

    init {
        if (playerHighlight.isEmpty()) {
            FakePlayerDataSource.dummyPlayers.forEach {
                playerHighlight.add(PlayerHighlight(it))
            }
        }
    }

    fun getAllPlayerHighlight(): Flow<List<PlayerHighlight>> {
        return flowOf(playerHighlight)
    }

    fun getPlayerHighlightById(playerId: Int): PlayerHighlight {
        return playerHighlight.first {
            it.player.id == playerId
        }
    }

    fun updatePlayerHighlight(playerId: Int): Flow<Boolean> {
        val index = playerHighlight.indexOfFirst { it.player.id == playerId }
        val result = if (index >= 0) {
            val player = playerHighlight[index]
            player.highlight = !player.highlight
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getHighlightPlayer(): Flow<List<PlayerHighlight>> {
        return getAllPlayerHighlight().map {
            it.filter { player ->
                player.highlight
            }
        }
    }

    fun searchPlayer(query: String): Flow<List<PlayerHighlight>> {
        return getAllPlayerHighlight().map {
            it.filter { player ->
                player.player.firstName.contains(query, true) || player.player.lastName.contains(query, true)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(): PlayerRepository =
            instance ?: synchronized(this) {
                PlayerRepository().apply {
                    instance = this
                }
            }
    }
}