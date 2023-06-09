package hr.hpatrik.lordofthequotes.view.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import hr.hpatrik.lordofthequotes.view.MainScreen
import hr.hpatrik.lordofthequotes.viewmodel.AuthenticationViewModel

@ExperimentalPagingApi
@Composable
fun RootNavGraph(navController: NavHostController) {
    val authenticationViewModel = viewModel<AuthenticationViewModel>()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTH) {
        authNavGraph(navController = navController, authenticationViewModel, context)
        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}