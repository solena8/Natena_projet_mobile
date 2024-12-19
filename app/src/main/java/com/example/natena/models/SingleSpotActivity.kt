package com.example.natena.models
import com.example.natena.R


data class Spot(
    val spotImage: Int,
    val spotName: String,
    val spotLocation: String
)

val spots = listOf(Spot(R.drawable.cap_ferret, "Plage du Bout", "Cap Ferret"),Spot(R.drawable.cap_ferret, "Plage du Bout", "Cap Ferret"))