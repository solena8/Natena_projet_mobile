package com.example.natena.models

import android.content.Context
import android.util.Log
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
    val records: List<SurfSpot>
)

//Transforme le Json en jsonString pour que Kotlin puisse le lire
fun parseJson(jsonString: String): SpotsRecords? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(SpotsRecords::class.java)
    return adapter.fromJson(jsonString)
}

fun readJsonFromRaw(context: Context, resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(inputStream.reader())
    return bufferedReader.use { it.readText() }
}

public fun associateDataToRightSpot(context: Context) {
    val jsonString = readJsonFromRaw(context, R.raw.first_datas)
    val parsedData = parseJson(jsonString)

    // Afficher les résultats
    parsedData?.records?.forEach { record ->
        val spot = Spot(
            spotImage = R.drawable.biarritz,
            spotName = record.surfBreak,
            spotLocation = record.address
        )
        jsonSpots.add(spot)

        jsonSpots.forEach { spot ->
            println("Spot: ${spot.spotName}, Location: ${spot.spotLocation}")
        }
    }
}
