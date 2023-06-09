package hr.hpatrik.lordofthequotes.view.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import hr.hpatrik.lordofthequotes.sdk.SpotifyModule
import hr.hpatrik.lordofthequotes.view.BottomNavScreen
import hr.hpatrik.lordofthequotes.view.main.AboutScreen
import hr.hpatrik.lordofthequotes.view.main.QuotesScreen
import hr.hpatrik.lordofthequotes.view.main.QuotesState
import hr.hpatrik.lordofthequotes.view.main.SoundtrackScreen
import hr.hpatrik.lordofthequotes.viewmodel.QuotesViewModel

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalPagingApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Quotes.route) {
        composable(route = BottomNavScreen.Quotes.route){
            val quotesViewModel = hiltViewModel<QuotesViewModel>()
            val quotesState = QuotesState(quotesViewModel)
            QuotesScreen(quotesState = quotesState)
        }
        composable(route = BottomNavScreen.Soundtracks.route){
            SoundtrackScreen()
        }
        composable(route = BottomNavScreen.About.route){
            AboutScreen(modifier = Modifier)
        }
    }
}