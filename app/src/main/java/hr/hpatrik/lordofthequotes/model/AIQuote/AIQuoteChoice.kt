package hr.hpatrik.lordofthequotes.model.AIQuote

data class AIQuoteChoice(
    val message: AIQuoteMessage,
    val finish_reason: String,
    val index: Int
)
