package hr.hpatrik.lordofthequotes.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hr.hpatrik.lordofthequotes.model.Quote

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes_table")
    fun getQuotes() : PagingSource<Int, Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes: List<Quote>)

    @Query("DELETE FROM quotes_table")
    suspend fun deleteQuotes()
}