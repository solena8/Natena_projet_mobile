package com.example.natena.models

import android.content.Context
import com.example.natena.R
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

// Classe qui permet de gérer les dates.
class DateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

    @FromJson
    fun fromJson(string: String): Date? {
        return try {
            //@Todo A modifier pour que la date retournée soit sous le format français.
            dateFormat.parse(string)
        } catch (e: Exception) {
            null
        }
    }

    //@Todo A modifier pour que la date retournée soit sous le format français.
    @ToJson
    fun toJson(date: Date): String {
        return dateFormat.format(date)
    }
}

//En soit, on n'est pas obligés de faire un DTO qui soit le miroir de notre JSON, mais si on avait le temps d'implémenter plusieurs features, on serait très content d'avoir un DTO complet
data class SpotsRecordsComplex(
    val records: List<SurfSpotComplex>,
    @Json(name = "offset") val offset: String
)

data class SurfSpotComplex(
    @Json(name = "id") val recordId: String,
    @Json(name = "fields") val fields: Field,
    @Json(name = "createdTime") val createdTime: Date?
)

data class Field(
    @Json(name = "Surf Break") val surfBreak: List<String>,
    @Json(name = "Difficulty Level") val difficulty: Int,
    @Json(name = "Destination") val destination: String,
    @Json(name = "Geocode") val geocode: String,
    @Json(name = "Influencers") val influencers: List<String>,
    @Json(name = "Magic Seaweed Link") val magicSeaweedLink: String,
    @Json(name = "Photos") val photos: List<Photos>,
    @Json(name = "Peak Surf Season Begins") val peakSurfSeasonBegins: String,
    @Json(name = "Destination State/Country") val destinationState: String,
    @Json(name = "Peak Surf Season Ends") val peakSurfSeasonEnds: String,
    @Json(name = "Address") val address: String
)

data class Photos(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "filename") val filename: String,
    @Json(name = "size") val int: Int,
    @Json(name = "type") val fileType: String,
    @Json(name = "thumbnails") val thumbnails: Thumbnails,
)

data class Thumbnails(
    @Json(name = "small") val small: PictureSize,
    @Json(name = "large") val large: PictureSize,
    @Json(name = "full") val full: PictureSize
)

data class PictureSize(
    @Json(name = "url") val url: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
)

fun parseComplexJson(jsonString: String): SpotsRecordsComplex? {
    val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(SpotsRecordsComplex::class.java)
    return adapter.fromJson(jsonString)
}

//Permet de lire la string json
fun readComplexJsonFromRaw(context: Context, resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val bufferedReader = BufferedReader(inputStream.reader())
    return bufferedReader.use { it.readText() }
}

fun createSpotsFromComplexJson(context: Context) {
    //Récupération du JSON sous le format string
    val jsonString = readComplexJsonFromRaw(context, R.raw.complex_datas)
    val parsedData = parseComplexJson(jsonString)

    // Création d'un spot pour chaque élément au sein de records après avoir vérifié que records n'est pas null
    parsedData?.records?.forEach { record ->
        val spot = Spot(
            spotImage = record.fields.photos[0].url,
            spotName = record.fields.surfBreak[0],
            spotLocation = record.fields.address
        )
        spots.add(spot)
    }
    // Contrôle en console des spots.
    spots.forEach { spot ->
        println("Spot: ${spot.spotName}, Location: ${spot.spotLocation}")
    }
}