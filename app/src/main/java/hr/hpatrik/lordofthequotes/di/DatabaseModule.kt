package hr.hpatrik.lordofthequotes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hr.hpatrik.lordofthequotes.db.QuotesDatabase
import javax.inject.Singleton

private const val QUOTE_DATABASE = "quote_database"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton // created at - Application#onCreate, destroyed at - Application#onDestroy
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : QuotesDatabase {
        return Room.databaseBuilder(
            context,
            QuotesDatabase::class.java,
            QUOTE_DATABASE
        ).fallbackToDestructiveMigration()
            .build()

    }
}