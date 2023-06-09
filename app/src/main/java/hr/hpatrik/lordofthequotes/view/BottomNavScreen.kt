package hr.hpatrik.lordofthequotes.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import hr.hpatrik.lordofthequotes.R

sealed class BottomNavScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object Quotes: BottomNavScreen(
        route = "quotes",
        title = R.string.quotes,
        icon = Icons.Default.Message
    )
    object Soundtracks: BottomNavScreen(
        route = "map",
        title = R.string.soundtracks,
        icon = Icons.Default.MusicNote
    )
    object About: BottomNavScreen(
        route = "about",
        title = R.string.about,
        icon = Icons.Default.Person
    )
}