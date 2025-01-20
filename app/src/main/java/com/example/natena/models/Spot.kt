package com.example.natena.models


data class Spot(
    val id: Int,
    val type: String,
    val address: String,
    val url: String
)

// Extension function pour convertir DTO en modèle
fun SpotDto.toSpot() = Spot(
    id = id,
    type = type,
    address = address,
    url = url
)
