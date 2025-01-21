package com.example.natena.models

import com.google.gson.annotations.SerializedName

data class SpotDto(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("address") val address: String,
    @SerializedName("url") val url: String
)

