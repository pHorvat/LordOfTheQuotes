package hr.hpatrik.lordofthequotes.model

data class AIQuoteRequestBody(
    val model: String,
    val messages: List<AIQuoteRequestMessage>,
    val temperature: Double
)
