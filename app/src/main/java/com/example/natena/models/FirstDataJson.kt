package com.example.natena.models

import android.content.Context
import com.example.natena.R
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader

//Une data class est une classe dédiée à la représentation des données
//Si on regarde ses attributs, ils correspondent à la structure du fichier Json res.raw.first_datas.json
data class SurfSpot(
    @Json(name = "Surf Break") val surfBreak: String,
    @Json(name = "Photos") val photos: String,
    @Json(name = "Address") val address: String
)

data class SpotsRecords(
    val records: List<SurfSpot>,
)

//Transforme le Json en jsonString pour que Kotlin puisse le lire
fun parseJson(jsonString: String): SpotsRecords? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(SpotsRecords::class.java)
    return adapter.fromJson(jsonString)
}

//Permet de lire la string json
fun readJsonFromRaw(context: Context, resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(inputStream.reader())
    return bufferedReader.use { it.readText() }
}

//
fun createSpotsFromJson(context: Context) {
    val jsonString = readJsonFromRaw(context, R.raw.first_datas)
    val parsedData = parseJson(jsonString)

    // Création d'un spot pour chaque élément au sein de records après avoir vérifié que records n'est pas null
    parsedData?.records?.forEach { record ->
        val spot = Spot(
            spotImage = record.photos,
            spotName = record.surfBreak,
            spotLocation = record.address
        )
        spots.add(spot)
    }
    // Contrôle en console des spots.
        spots.forEach { spot ->
            println("Spot: ${spot.spotName}, Location: ${spot.spotLocation}")
        }
}
