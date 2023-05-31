package com.fathoor.cityzen.presentation.screen.highlight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fathoor.cityzen.data.local.PlayerHighlight
import com.fathoor.cityzen.data.repository.PlayerRepository
import com.fathoor.cityzen.presentation.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HighlightViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<PlayerHighlight>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getHighlightPlayer() {
        viewModelScope.launch {
            repository.getHighlightPlayer()
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect { playerHighlight ->
                    if (playerHighlight.isEmpty()) {
                        _noMatch.value = true
                        _result.value = Result.Success(emptyList())
                    } else {
                        _noMatch.value = false
                        _result.value = Result.Success(playerHighlight)
                    }
                }
        }
    }
}