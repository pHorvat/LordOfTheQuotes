package hr.hpatrik.lordofthequotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import hr.hpatrik.lordofthequotes.ui.theme.LordOfTheQuotesTheme
import hr.hpatrik.lordofthequotes.view.MainScreen
import hr.hpatrik.lordofthequotes.view.nav.RootNavGraph
import hr.hpatrik.lordofthequotes.viewmodel.SpotifyStateVM

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val spotifyStateVM = hiltViewModel<SpotifyStateVM>()
            spotifyStateVM.initSpotify(LocalContext.current)
            LordOfTheQuotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RootNavGraph(navController = rememberNavController())
                }
            }
        }
    }

}

