package com.example.natena.network

import com.example.natena.BuildConfig
import com.example.natena.models.Photos
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import okhttp3.MediaType.Companion.toMediaType

const val BASE_ID = BuildConfig.BASE_ID
const val TABLE_ID = BuildConfig.TABLE_ID
const val API_KEY = BuildConfig.API_KEY

private const val BASE_URL =
    "https://api.airtable.com/v0/$BASE_ID/$TABLE_ID"

val apiInterceptor = Interceptor { chain ->
    val request: Request = chain.request().newBuilder()
        .addHeader("Authorization", "Bearer $API_KEY")
        .build()
    chain.proceed(request)
}

val client = OkHttpClient.Builder()
    .addInterceptor(apiInterceptor)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface SurfSpotApiService {
    @GET("Photos")
    suspend fun getSpotPhotos(): List<Photos>

    @GET("fldakCEOY7OpvS3RR")
    suspend fun getSpotName(): String

    @GET("Address")
    suspend fun getSpotLocation(): String
}

object SurfSpotApi {
    val retrofitService: SurfSpotApiService by lazy {
        retrofit.create(SurfSpotApiService::class.java)
    }
}

