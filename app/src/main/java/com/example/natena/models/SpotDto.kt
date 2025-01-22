package com.example.natena.models

//These data classes are crucial for seamlessly converting the API's JSON response into Kotlin
// objects that can be easily used throughout the application.

import com.google.gson.annotations.SerializedName


// Data class representing a single spot as received from the API
data class SpotDto(
    // Unique identifier for the spot
    @SerializedName("id") val id: Int,

    // Type or category of the spot
    @SerializedName("type") val type: String,

    // Address or location of the spot
    @SerializedName("address") val address: String,

    // URL for an image or additional information about the spot
    @SerializedName("url") val url: String,

    // Indicates if this is a main spot (1) or not (0)
    @SerializedName("main") val main: Int,

    // Latitude coordinate of the spot
    @SerializedName("latitude") val latitude: Double,

    // Longitude coordinate of the spot
    @SerializedName("longitude") val longitude: Double,

    // spot difficulty level (between 1 and 5)
    @SerializedName("difficulty") val difficulty: Int

)
