package hr.hpatrik.lordofthequotes.view.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toIcon
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Artist
import com.spotify.protocol.types.Track
import hr.hpatrik.lordofthequotes.sdk.SpotifyModule
import hr.hpatrik.lordofthequotes.sdk.TrackItems
import hr.hpatrik.lordofthequotes.viewmodel.SpotifyStateVM

@Composable
fun SoundtrackScreen() {
    var isVisible by remember { mutableStateOf(false) }
    val boxModifier = if (isVisible) {
        Modifier.padding(bottom = 60.dp)
    } else {
        Modifier
    }


    val spotifyViewModel = hiltViewModel<SpotifyStateVM>()
    if (spotifyViewModel.spotify.value.isLoading) {
        Text(text = "Loading")
        return
    }
    val spotify = spotifyViewModel.spotify.value.spotifyModuleValue
    var isPlaying by remember {
        mutableStateOf(false)
    }
    Log.d("COMPOSABLE_TRACKS:", spotify.playlistTracks.toString())
    fun onCardClick(uri: String) {
        spotify.playTrack(uri)
        Log.d("COMPOSABLE_TRACKS:", spotify.playlistTracks.toString())
        isPlaying = true
        isVisible = true
    }

    Box(modifier = boxModifier) {
        LazyColumn(modifier = Modifier.padding(bottom = 60.dp)) {
            items(items = spotify.playlistTracks) { myObject ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable { onCardClick(myObject.uri) },
                    elevation = 4.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            bitmap = myObject.imageUri!!.asImageBitmap(),
                            contentDescription = "Track Image",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column() {
                            Text(text = myObject.title, fontWeight = FontWeight.Bold)
                            Text(text = myObject.subtitle)
                        }

                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        //Player controls
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .align(BottomCenter),
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        spotify.trackImage.value?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "audio icon",
                                modifier = Modifier.height(50.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .width(230.dp)
                        ) {
                            Text(text = spotify.trackName.value)
                            Text(
                                text = spotify.trackArtist.value,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (isPlaying) {
                                isPlaying = false
                                spotify.pauseTrack()
                            } else {
                                isPlaying = true
                                spotify.resumeTrack()
                            }

                        }) {
                            Icon(
                                imageVector = if (!isPlaying) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                                contentDescription = "play/pause button",
                            )
                        }

                    }
                }
            }
        }
    }
}








