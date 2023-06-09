package hr.hpatrik.lordofthequotes.model

data class AIQuoteRequestMessage(
    val role: String,
    val content: String
)