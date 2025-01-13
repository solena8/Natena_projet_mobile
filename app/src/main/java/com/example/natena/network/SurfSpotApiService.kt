package com.example.natena.network

import com.example.natena.BuildConfig
import com.example.natena.models.Photos
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BACK_API_URL = BuildConfig.BACK_API_URL
// Interceptor pour ajouter des entêtes ou autres transformations
val apiInterceptor = Interceptor { chain ->
    val request: Request = chain.request().newBuilder()
        .build()
    chain.proceed(request)
}

// Client OkHttp configuré avec les interceptors
val client = OkHttpClient.Builder()
    .addInterceptor(apiInterceptor)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

// Retrofit instance
private val retrofit = Retrofit.Builder()
    .baseUrl(BACK_API_URL)
    .addConverterFactory(GsonConverterFactory.create())  // Convertisseur JSON
    .client(client)  // Ajout du client OkHttp
    .build()

// Service d'API
interface SurfSpotApiService {
    @GET("/")  // Endpoint pour récupérer les données
    suspend fun getAllItems(): Map<String, Any>  // Retourne les données sous forme de dictionnaire
}

// Singleton pour l'API
object SurfSpotApi {
    val retrofitService: SurfSpotApiService by lazy {
        retrofit.create(SurfSpotApiService::class.java)
    }
}

// Fonction pour récupérer les données (via Retrofit)
suspend fun fetchAllItems(): Map<String, Any> {
        val response = SurfSpotApi.retrofitService.getAllItems()
        return response

}



