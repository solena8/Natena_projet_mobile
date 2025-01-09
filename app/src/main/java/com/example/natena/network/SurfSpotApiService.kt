package com.example.natena.network

import com.example.natena.BuildConfig

const val BASE_ID = BuildConfig.BASE_ID
const val TABLE_ID = BuildConfig.TABLE_ID
const val API_KEY = BuildConfig.API_KEY

private const val BASE_URL =
    "https://api.airtable.com/v0/$BASE_ID/$TABLE_ID"

