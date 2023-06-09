package hr.hpatrik.lordofthequotes.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Movie(
    @SerialName("_id")
    val id: String,
    val name: String,
    val runtimeInMinutes: Long,
    val budgetInMillions: Long,
    val boxOfficeRevenueInMillions: Long,
    val academyAwardNominations: Long,
    val academyAwardWins: Long,
    val rottenTomatoesScore: Long,

    )
