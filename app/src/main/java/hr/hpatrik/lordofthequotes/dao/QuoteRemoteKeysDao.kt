package hr.hpatrik.lordofthequotes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hr.hpatrik.lordofthequotes.model.QuoteRemoteKeys

@Dao
interface QuoteRemoteKeysDao {
    @Query("SELECT * FROM quote_remote_keys_table WHERE id=:id")
    suspend fun getQuoteRemoteKeys(id: String) : QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuoteRemoteKeys(quoteRemoteKeys: List<QuoteRemoteKeys>)

    @Query("DELETE FROM quote_remote_keys_table")
    suspend fun deleteQuoteRemoteKeys()
}