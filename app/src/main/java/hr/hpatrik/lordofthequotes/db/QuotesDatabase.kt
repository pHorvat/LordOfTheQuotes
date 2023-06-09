package hr.hpatrik.lordofthequotes.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import hr.hpatrik.lordofthequotes.dao.QuoteRemoteKeysDao
import hr.hpatrik.lordofthequotes.dao.QuotesDao
import hr.hpatrik.lordofthequotes.model.Quote
import hr.hpatrik.lordofthequotes.model.QuoteRemoteKeys


@Database(entities = [Quote::class, QuoteRemoteKeys::class], version = 4, exportSchema = true)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
    abstract fun quoteRemoteKeysDao(): QuoteRemoteKeysDao
}