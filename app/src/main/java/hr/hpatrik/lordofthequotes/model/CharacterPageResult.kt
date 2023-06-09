package hr.hpatrik.lordofthequotes.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class CharacterPageResult(
    @SerialName("docs")
    val character: List<Character>
)
