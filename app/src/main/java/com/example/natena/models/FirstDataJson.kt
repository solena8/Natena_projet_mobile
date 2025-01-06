package com.example.natena.models

import android.content.Context
import com.example.natena.R
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader

//Une data class est une classe dédiée à la représentation des données
//Si on regarde ses attributs, ils correspondent à la structure du fichier Json res.raw.first_datas.json
data class SurfRecord(
    @Json(name = "Surf Break") val surfBreak: String,
    @Json(name = "Photos") val photos: String,
    @Json(name = "Address") val address: String
)

data class SurfData(
    val records: List<SurfRecord>
)

//Transforme le Json en jsonString pour que Kotlin puisse le lire
fun parseJson(jsonString: String): SurfData? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(SurfData::class.java)
    return adapter.fromJson(jsonString)
}

fun readJsonFromRaw(context: Context, resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(inputStream.reader())
    return bufferedReader.use { it.readText() }
}

//Fonction à intégrer à nos fichiers SpotAdapter & SingleSpotActivity
fun associateDataToRightSpot(context: Context) {
    val jsonString = readJsonFromRaw(context, R.raw.first_datas)
    val parsedData = parseJson(jsonString)

    // Afficher les résultats
    parsedData?.records?.forEach { record ->
        val surfBreak = ("Surf Break: ${record.surfBreak}")
        val photo = ("Photos: ${record.photos}")
        val address = ("Address: ${record.address}")
    }
}
