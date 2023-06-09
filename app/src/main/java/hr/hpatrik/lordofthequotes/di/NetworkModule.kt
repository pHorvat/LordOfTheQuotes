package hr.hpatrik.lordofthequotes.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.hpatrik.lordofthequotes.api.AIQuoteApi
import hr.hpatrik.lordofthequotes.api.QuotesApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton



private const val QUOTES_API_URL = "https://the-one-api.dev/v2/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton // created at - Application#onCreate, destroyed at - Application#onDestroy
    fun provideHttpClient(): OkHttpClient {
        val requestCounter = AtomicInteger(0)
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if(message.contains(">")){
                    Log.d("Retrofit", "[Request ${requestCounter.incrementAndGet()}] $message") // Log the message with the request counter to Logcat
                }else{
                    Log.d("Retrofit", "Response: $message") // Log the message to Logcat with the "Retrofit" tag

                }
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }
/*
    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun providePointsApi(okHttpClient: OkHttpClient) : PointsApi {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(POINTS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PointsApi::class.java)
    }
*/
    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideQuotesApi(okHttpClient: OkHttpClient) : QuotesApi {
        val json = Json {
            ignoreUnknownKeys = true
        }



        return Retrofit.Builder()
            .baseUrl(QUOTES_API_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(QuotesApi::class.java)
    }




}