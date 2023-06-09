package hr.hpatrik.lordofthequotes.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class QuotePageResult (
    @SerialName("docs")
    var quotes: List<Quote>
)