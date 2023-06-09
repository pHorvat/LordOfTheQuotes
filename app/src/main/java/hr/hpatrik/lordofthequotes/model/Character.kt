package hr.hpatrik.lordofthequotes.model


import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Character(
    @SerialName("_id")
    val id: String,
    val height: String,
    val race: String,
    val gender: String,
    val birth: String,
    val spouse: String,
    val death: String,
    val realm: String,
    val hair: String,
    val name: String,
    val wikiUrl: String,

)
