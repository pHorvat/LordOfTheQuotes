package hr.hpatrik.lordofthequotes.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.hpatrik.lordofthequotes.State.SpotifyState
import hr.hpatrik.lordofthequotes.sdk.SpotifyModule
import javax.inject.Inject

@HiltViewModel
class SpotifyStateVM @Inject constructor(
    private val _spotifyState: MutableState<SpotifyState>
) :ViewModel() {
    val spotify: State<SpotifyState> get() = _spotifyState

    fun initSpotify(context: Context){
        var spotifyModule = SpotifyModule()
        spotifyModule.onStart(context) {
            _spotifyState.value =
                _spotifyState.value.copy(spotifyModule = spotifyModule, isLoading = false)
        }
    }
}