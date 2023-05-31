package com.fathoor.cityzen.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fathoor.cityzen.data.local.PlayerHighlight
import com.fathoor.cityzen.data.repository.PlayerRepository
import com.fathoor.cityzen.presentation.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<PlayerHighlight>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    fun getPlayerHighlightById(playerId: Int) {
        viewModelScope.launch {
            _result.value = Result.Loading
            _result.value = Result.Success(repository.getPlayerHighlightById(playerId))
        }
    }

    fun updateHighlight(playerId: Int) {
        viewModelScope.launch {
            repository.updatePlayerHighlight(playerId)
        }
    }
}