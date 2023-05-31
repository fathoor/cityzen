package com.fathoor.cityzen.presentation.screen.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fathoor.cityzen.presentation.common.Result
import com.fathoor.cityzen.presentation.common.ViewModelFactory
import com.fathoor.cityzen.presentation.component.DetailItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    playerId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory()
    ),
    navigateBack: () -> Unit,
    navigateToHighlight: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.background
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDark
        )
    }

    viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
        when (result) {
            is Result.Loading -> {
                viewModel.getPlayerHighlightById(playerId)
            }
            is Result.Success -> {
                val playerHighlight = result.data
                val player = playerHighlight.player
                DetailContent(
                    modifier = modifier,
                    id = player.id,
                    firstName = player.firstName,
                    lastName = player.lastName,
                    nationality = player.nationality,
                    position = player.position,
                    number = player.number,
                    image = player.photoUrl,
                    highlight = playerHighlight.highlight,
                    onBackClick = navigateBack,
                    onUpdateHighlight = {
                        viewModel.updateHighlight(playerId)
                        navigateToHighlight()
                    }
                )
            }
            is Result.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    id: Int,
    firstName: String,
    lastName: String,
    nationality: String,
    position: String,
    number: Int,
    image: String,
    highlight: Boolean,
    onBackClick: () -> Unit,
    onUpdateHighlight: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        DetailItem(
            id = id,
            firstName = firstName,
            lastName = lastName,
            nationality = nationality,
            position = position,
            number = number,
            image = image,
            highlight = highlight,
            onBackClick = onBackClick,
            onUpdateHighlight = onUpdateHighlight
        )
    }
}