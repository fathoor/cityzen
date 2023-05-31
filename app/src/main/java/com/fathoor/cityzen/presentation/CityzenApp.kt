package com.fathoor.cityzen.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fathoor.cityzen.presentation.component.BottomBar
import com.fathoor.cityzen.presentation.navigation.Screen
import com.fathoor.cityzen.presentation.screen.detail.DetailScreen
import com.fathoor.cityzen.presentation.screen.highlight.HighlightScreen
import com.fathoor.cityzen.presentation.screen.home.HomeScreen
import com.fathoor.cityzen.presentation.screen.profile.ProfileScreen

@Composable
fun CityzenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = { playerId ->
                            navController.navigate(Screen.Detail.createRoute(playerId))
                        },
                    )
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                ) {
                    DetailScreen(
                        playerId = it.arguments?.getInt("playerId") ?: 0,
                        navigateBack = {
                            navController.navigateUp()
                        },
                        navigateToHighlight = {
                            navController.popBackStack()
                            navController.navigate(Screen.Highlight.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                composable(Screen.Highlight.route) {
                    HighlightScreen(
                        navigateToDetail = { playerId ->
                            navController.navigate(Screen.Detail.createRoute(playerId))
                        },
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    )
}