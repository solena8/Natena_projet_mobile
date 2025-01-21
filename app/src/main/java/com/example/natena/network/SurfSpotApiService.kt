package com.example.natena.network

import com.example.natena.BuildConfig
import com.example.natena.models.SpotDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// URL de base de l'API récupérée depuis les variables de build
const val BACK_API_URL = BuildConfig.BACK_API_URL

// Définition d'un intercepteur personnalisé pour OkHttp
// Permet de modifier ou d'ajouter des éléments à chaque requête (headers, params, etc.)
val apiInterceptor = Interceptor { chain ->
    val request: Request = chain.request().newBuilder()
        .build()
    chain.proceed(request)
}

// Configuration du client HTTP avec OkHttp
// Ajout de deux intercepteurs :
// 1. Notre intercepteur personnalisé
// 2. Un intercepteur de logging pour voir les détails des requêtes/réponses
val client = OkHttpClient.Builder()
    .addInterceptor(apiInterceptor)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

// Configuration de Retrofit
// - Définition de l'URL de base
// - Ajout du convertisseur GSON pour le parsing JSON
// - Configuration du client OkHttp
private val retrofit = Retrofit.Builder()
    .baseUrl(BACK_API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

// Interface définissant les endpoints de l'API
// Chaque méthode représente un endpoint différent
data class SpotResponse(
    val spots: List<SpotDto>
)

interface SurfSpotApiService {
    @GET("/all")
    suspend fun getAllSpots(): SpotResponse

    @GET("/spot/{id}")
    suspend fun getSpotById(@Path("id") id: String): SpotResponse
}




// Object singleton pour accéder à l'API
// Utilisation du pattern Singleton pour garantir une seule instance
object SurfSpotApi {
    // Création lazy (à la demande) du service
    val retrofitService: SurfSpotApiService by lazy {
        retrofit.create(SurfSpotApiService::class.java)
    }
}

// Fonction utilitaire pour récupérer les données
// Fonction suspendue pour être utilisée avec les coroutines
suspend fun fetchAllSpots(): SpotResponse {
    return SurfSpotApi.retrofitService.getAllSpots()

}


suspend fun fetchSpotById(id: String): SpotDto {
    val response = SurfSpotApi.retrofitService.getSpotById(id)
    return response.spots.firstOrNull()
        ?: throw NoSuchElementException("No spot found with id: $id")
}





