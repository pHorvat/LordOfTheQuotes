package hr.hpatrik.lordofthequotes.api

import hr.hpatrik.lordofthequotes.model.CharacterPageResult
import hr.hpatrik.lordofthequotes.model.MoviePageResult
import hr.hpatrik.lordofthequotes.model.QuotePageResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.random.Random

interface QuotesApi {
    @GET("quote")
    suspend fun getQuotes(
        @Header("Authorization") apiAuth: String = "Bearer <TheOneAPIToken>",
        @Query("limit") limit: Int = 5,
        @Query("page") page: Int = 1,

        ) : QuotePageResult

    @GET("character/{id}")
    suspend fun getCharacters(
        @Header("Authorization") apiAuth: String = "Bearer  <TheOneAPIToken>",
        @Path("id") id: String


    ) : CharacterPageResult

    @GET("movie/{id}")
    suspend fun getMovie(
        @Header("Authorization") apiAuth: String = "Bearer  <TheOneAPIToken>",
        @Path("id") id: String


    ) : MoviePageResult
}