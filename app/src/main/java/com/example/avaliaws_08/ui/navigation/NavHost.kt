package com.example.avaliaws_08.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.avaliaws_08.ui.screens.CadastroCompetidorScreen
import com.example.avaliaws_08.ui.screens.EntregaScreen
import com.example.avaliaws_08.ui.screens.HomeScreen
import com.example.avaliaws_08.ui.screens.ListaCompetidoresScreen
import com.example.avaliaws_08.ui.screens.RankingScreen
import com.example.avaliaws_08.ui.screens.ResultadoScreen
import com.example.avaliaws_08.ui.screens.ResumoPenalidadesScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.CADASTRO_COMPETIDOR) {
            CadastroCompetidorScreen(navController)
        }

        composable(Routes.LISTA_COMPETIDORES) {
            ListaCompetidoresScreen(navController)
        }

        composable(
            route = Routes.ENTREGA_COM_ID,
            arguments = listOf(navArgument("competidorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val competidorId = backStackEntry.arguments?.getInt("competidorId") ?: 0
            EntregaScreen(navController, competidorId)
        }

        composable(
            route = Routes.RESULTADO_COM_ID,
            arguments = listOf(navArgument("competidorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val competidorId = backStackEntry.arguments?.getInt("competidorId") ?: 0
            ResultadoScreen(navController, competidorId)
        }

        composable(Routes.RANKING) {
            RankingScreen(navController)
        }

        composable(Routes.RESUMO_PENALIDADES) {
            ResumoPenalidadesScreen(navController)
        }
    }
}
