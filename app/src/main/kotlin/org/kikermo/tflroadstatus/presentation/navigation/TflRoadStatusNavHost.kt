package org.kikermo.tflroadstatus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.kikermo.tflroadstatus.presentation.roadstatus.RoadStatusScreen
import org.kikermo.tflroadstatus.presentation.search.SearchScreen


@Composable
fun TflRoadStatusNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "search"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("search") {
            SearchScreen(
                statusDetailsNavigation = { roadId -> navController.navigate("raodStatus/$roadId") }
            )
        }
        composable(
            "raodStatus/{roadId}",
            arguments = listOf(navArgument("roadId") { type = NavType.StringType })
        ) {
            RoadStatusScreen(onBackPressed = { navController.popBackStack() })
        }
    }
}
