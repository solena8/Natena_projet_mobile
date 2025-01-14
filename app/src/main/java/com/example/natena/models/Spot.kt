package com.example.natena.models


data class Spot(
    val spotName: String,
    val spotLocation: String,
    val spotImage: String
)

val spots: MutableList<Spot> = mutableListOf()

