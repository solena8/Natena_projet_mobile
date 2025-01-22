package com.example.natena.network

import com.example.natena.BuildConfig
import com.example.natena.models.SpotDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Base URL for the API, retrieved from build variables
const val BACK_API_URL = BuildConfig.BACK_API_URL

// Custom interceptor for OkHttp
// Allows modifying or adding elements to each request (headers, params, etc.)
val apiInterceptor = Interceptor { chain ->
    val request: Request = chain.request().newBuilder()
        .build()
    chain.proceed(request)
}

// OkHttp client configuration
// Adding two interceptors:
// 1. Our custom interceptor
// 2. A logging interceptor to see request/response details
val client = OkHttpClient.Builder()
    .addInterceptor(apiInterceptor)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

// Retrofit configuration
// - Setting the base URL
// - Adding GSON converter for JSON parsing
// - Configuring the OkHttp client
private val retrofit = Retrofit.Builder()
    .baseUrl(BACK_API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

// Data class for the API response
data class SpotResponse(
    val spots: List<SpotDto>
)

// Interface defining the API endpoints
// Each method represents a different endpoint
interface SurfSpotApiService {
    @GET("/all")
    suspend fun getAllSpots(): SpotResponse

    @GET("/spot/{id}")
    suspend fun getSpotById(@Path("id") id: String): SpotResponse

    @POST("/spots")
    suspend fun addSpot(@Body spot: SpotDto): Response<SpotDto>
}

// Singleton object to access the API
// Using Singleton pattern to ensure a single instance
object SurfSpotApi {
    // Lazy creation of the service
    val retrofitService: SurfSpotApiService by lazy {
        retrofit.create(SurfSpotApiService::class.java)
    }
}

// Utility function to fetch all spots
// Suspended function to be used with coroutines
suspend fun fetchAllSpots(): SpotResponse {
    return SurfSpotApi.retrofitService.getAllSpots()
}

// Utility function to fetch a spot by ID
// Suspended function to be used with coroutines
suspend fun fetchSpotById(id: String): SpotDto {
    val response = SurfSpotApi.retrofitService.getSpotById(id)
    return response.spots.firstOrNull()
        ?: throw NoSuchElementException("No spot found with id: $id")
}
