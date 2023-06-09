package hr.hpatrik.lordofthequotes.State

import hr.hpatrik.lordofthequotes.sdk.SpotifyModule

data class SpotifyState(
    var spotifyModule: SpotifyModule? = null,
    var isLoading: Boolean = true
){
    val spotifyModuleValue: SpotifyModule get() = spotifyModule!!
}
