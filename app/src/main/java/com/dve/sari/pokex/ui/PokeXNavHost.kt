package com.dve.sari.pokex.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dve.sari.minipokedex.ui.home.HomeScreen
import com.dve.sari.minipokedex.ui.info.InfoScreen

@Composable
fun PokeXNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route
    ) {
        composable(
            route = Destination.Home.route
        ) {
            HomeScreen(
                onItemClicked = { id ->
                    val route = "info/$id"
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = Destination.Info.route,
        ) {
            val id = it.arguments?.getString("id")
            InfoScreen(
                id = id.orEmpty(),
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}

sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data object Info : Destination("info/{id}")
}