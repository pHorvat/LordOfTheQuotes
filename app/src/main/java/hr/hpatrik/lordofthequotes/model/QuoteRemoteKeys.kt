package hr.hpatrik.lordofthequotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_remote_keys_table")
data class QuoteRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?

    )
