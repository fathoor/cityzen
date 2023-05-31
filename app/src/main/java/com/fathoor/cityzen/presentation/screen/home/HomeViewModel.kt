package com.fathoor.cityzen.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fathoor.cityzen.data.local.PlayerHighlight
import com.fathoor.cityzen.data.repository.PlayerRepository
import com.fathoor.cityzen.presentation.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<PlayerHighlight>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getAllPlayerHighlight() {
        viewModelScope.launch {
            repository.getAllPlayerHighlight()
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { playerHighlight ->
                    _result.value = Result.Success(playerHighlight)
                }
        }
    }

    fun searchPlayer(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.searchPlayer(query)
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
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