package com.fathoor.cityzen.presentation.screen.highlight

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fathoor.cityzen.R
import com.fathoor.cityzen.data.local.PlayerHighlight
import com.fathoor.cityzen.presentation.common.Result
import com.fathoor.cityzen.presentation.common.ViewModelFactory
import com.fathoor.cityzen.presentation.component.PlayerHighlightItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HighlightScreen(
    modifier: Modifier = Modifier,
    viewModel: HighlightViewModel = viewModel(
        factory = ViewModelFactory()
    ),
    navigateToDetail: (Int) -> Unit,
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
                viewModel.getHighlightPlayer()
            }
            is Result.Success -> {
                HighlightContent(
                    modifier = modifier,
                    playerHighlight = result.data,
                    noMatch = viewModel.noMatch.value,
                    navigateToDetail = navigateToDetail,
                )
            }
            is Result.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighlightContent(
    modifier: Modifier = Modifier,
    playerHighlight: List<PlayerHighlight>,
    noMatch: Boolean,
    navigateToDetail: (Int) -> Unit,
) {
    if (noMatch) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(R.string.no_data))
        }
    } else {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = playerHighlight,
                    key = { it.player.id }
                ) {data ->
                    PlayerHighlightItem(
                        image = data.player.photoUrl,
                        number = data.player.number,
                        firstName = data.player.firstName,
                        lastName = data.player.lastName,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.player.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}