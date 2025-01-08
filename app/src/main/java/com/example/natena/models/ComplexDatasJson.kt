package com.example.natena.models

import com.squareup.moshi.Json
import java.util.Date

data class SpotsRecordsComplex(
    val records: List<SurfSpotComplex>
)

data class SurfSpotComplex(
    @Json(name = "id") val recordId: String,
    @Json(name = "fields") val fields: Field,
    @Json(name = "createdTime") val createdTime: Date
)

data class Field(
    @Json(name = "Surf Break") val surfBreak: Array<String>,
    @Json(name = "Difficulty Level") val difficulty: Int,
    @Json(name = "Destination") val destination: String,
    @Json(name = "Geocode") val geocode: String,
    @Json(name = "Influencers") val influencers: Array<String>,
    @Json(name = "Magic Seaweed Link") val magicSeaweedLink: Array<String>,
    @Json(name = "Photos") val photos: Photos,
    @Json(name = "Peak Surf Season Begins") val peakSurfSeasonBegins: String,
    @Json(name = "Destination State/Country") val destinationState: String,
    @Json(name = "Peak Surf Season End") val peakSurfSeasonEnds: String,
    @Json(name = "Address") val address: String
)

data class Photos(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "filename") val filename: String,
    @Json(name = "size") val Int: String,
    @Json(name = "type") val fileType: String,
    @Json(name = "thumbnails") val thumbnails: PictureSize,
)

data class PictureSize(
    @Json(name = "url") val url: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
)