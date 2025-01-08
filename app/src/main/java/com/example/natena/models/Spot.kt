package com.example.natena.models


data class Spot(
    val spotImage: String,
    val spotName: String,
    val spotLocation: String
)

val spots: MutableList<Spot> = mutableListOf()

