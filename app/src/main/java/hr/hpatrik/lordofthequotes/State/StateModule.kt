package hr.hpatrik.lordofthequotes.State

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StateModule {

    @Provides
    @Singleton
    fun provideSpotifyState(): MutableState<SpotifyState> {
        return mutableStateOf(SpotifyState())
    }

}