package hr.hpatrik.lordofthequotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
@kotlinx.serialization.Serializable
data class Quote(
    @PrimaryKey(autoGenerate = true)
    @kotlinx.serialization.Transient
    val quoteId: Int = 0,
    val id: String,
    val dialog: String,
    val movie: String,
    val character: String,
    @kotlinx.serialization.Transient
    var height: String = "",
    @kotlinx.serialization.Transient
    var race: String = "",
    @kotlinx.serialization.Transient
    var gender: String = "",
    @kotlinx.serialization.Transient
    var birth: String= "",
    @kotlinx.serialization.Transient
    var spouse: String= "",
    @kotlinx.serialization.Transient
    var death: String= "",
    @kotlinx.serialization.Transient
    var realm: String= "",
    @kotlinx.serialization.Transient
    var hair: String= "",
    @kotlinx.serialization.Transient
    var name: String= "",
    @kotlinx.serialization.Transient
    var wikiUrl: String= "",
    @kotlinx.serialization.Transient
    var movieName: String= "",
    @kotlinx.serialization.Transient
    var runtimeInMinutes: Long= 0,
    @kotlinx.serialization.Transient
    var budgetInMillions: Long= 0,
    @kotlinx.serialization.Transient
    var boxOfficeRevenueInMillions: Long= 0,
    @kotlinx.serialization.Transient
    var academyAwardNominations: Long= 0,
    @kotlinx.serialization.Transient
    var academyAwardWins: Long= 0,
    @kotlinx.serialization.Transient
    var rottenTomatoesScore: Long= 0,

    )
