package hr.hpatrik.lordofthequotes.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MoviePageResult(
    @SerialName("docs")
    val movie: List<Movie>
)
