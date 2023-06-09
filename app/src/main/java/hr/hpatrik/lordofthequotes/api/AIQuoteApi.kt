package hr.hpatrik.lordofthequotes.api

import hr.hpatrik.lordofthequotes.model.AIQuoteRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AIQuoteApi {
    @POST("completions")
    suspend fun getAIQuote(
        @Header("Authorization") apiAuth: String = "Bearer <OpenAIToken>",
        @Body requestBody: AIQuoteRequestBody) : Response<ResponseBody>
}