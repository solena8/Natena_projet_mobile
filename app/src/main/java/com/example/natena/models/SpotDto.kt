package com.example.natena.models

import com.google.gson.annotations.SerializedName

data class SpotResponse(
    val spots: List<SpotDto>
)

data class SpotDto(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("address") val address: String,
    @SerializedName("url") val url: String,
    @SerializedName("main") val main: Int,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
