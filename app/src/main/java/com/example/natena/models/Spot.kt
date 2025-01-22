package com.example.natena.models



//This class defines the structure of a Spot object, which includes properties such as id, type,
// address, and url. Each property is essential for identifying and describing a spot.
data class Spot(
    val id: Int,          // Unique identifier for the spot
    val type: String,     // Type or category of the spot
    val address: String,  // Address or location of the spot
    val url: String,       // URL for an image or additional information about the spot
    val difficulty: Int
)

//The toSpot() function is an extension function that converts an instance of SpotDto (which is
// typically used for data transfer) into a Spot object. This function allows for
// easy transformation and mapping of data between different representations in the application
fun SpotDto.toSpot() = Spot(
    id = id,             // Map id from SpotDto to Spot
    type = type,         // Map type from SpotDto to Spot
    address = address,   // Map address from SpotDto to Spot
    url = url,            // Map url from SpotDto to Spot
    difficulty = difficulty
)
