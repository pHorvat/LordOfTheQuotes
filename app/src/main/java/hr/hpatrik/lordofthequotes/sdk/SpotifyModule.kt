package hr.hpatrik.lordofthequotes.sdk

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ListItem


class SpotifyModule() {

    private val clientId = "b38c8fbc41de4c8487556e8a5da20cd9"
    private val redirectUri = "LordOfTheQuotes://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    var playlistTracks = mutableListOf<TrackItems>()
    var trackName = mutableStateOf("")
    var trackArtist = mutableStateOf("")
    var trackImage = mutableStateOf<Bitmap?>(null)

    fun onStart(context: Context, onConnectedCallback: (SpotifyAppRemote) -> Unit) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()
        SpotifyAppRemote.connect(
            context,
            connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(appRemote: SpotifyAppRemote) {
                    spotifyAppRemote = appRemote
                    getTracksFromPlaylist()
                    onConnectedCallback(appRemote)
                    Log.d("MainActivity", "Connected! Yay!")
                    // Now you can start interacting with App Remote

                    Log.d("LOTR_PLAYLIST: ", playlistTracks.toString())
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", throwable.message, throwable)
                    // Something went wrong when attempting to connect! Handle errors here
                }
            })
    }

    fun getTracksFromPlaylist() {
        val playlist = ListItem(
            "spotify:playlist:4D6KMociXdmhv4lhQe1v7J",
            "spotify:playlist:4D6KMociXdmhv4lhQe1v7J",
            null,
            "",
            "",
            false,
            true
        )
        spotifyAppRemote?.contentApi?.getChildrenOfItem(playlist, 100, 0)
            ?.setResultCallback { listItems ->
                for (trck in listItems.items) {
                    spotifyAppRemote!!.imagesApi.getImage(trck.imageUri)
                        ?.setResultCallback { items ->
                            val title = trck.title
                            val id = trck.id
                            val uri = trck.uri
                            val imageUri = items
                            val subtitle = trck.subtitle
                            playlistTracks.add(TrackItems(id, title, uri, imageUri, subtitle))

                        }

                }
            }
    }

    fun resumeTrack() {
        spotifyAppRemote?.let {
            it.playerApi.resume()
        }
    }

    fun pauseTrack() {
        spotifyAppRemote?.let {
            it.playerApi.pause()
        }
    }

    fun playTrack(trackURI: String) {

        spotifyAppRemote?.let {
            // Play a playlist
            it.playerApi.play(trackURI)
            // Subscribe to PlayerState

            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)
                trackName.value = track.name
                trackArtist.value = track.artist.name
                spotifyAppRemote!!.imagesApi.getImage(track.imageUri)?.setResultCallback { items ->
                    trackImage.value = items
                }

            }
        }
    }

}

