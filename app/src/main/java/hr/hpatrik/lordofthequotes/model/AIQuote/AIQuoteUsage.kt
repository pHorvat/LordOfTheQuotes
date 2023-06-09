package hr.hpatrik.lordofthequotes.model.AIQuote

data class AIQuoteUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)
