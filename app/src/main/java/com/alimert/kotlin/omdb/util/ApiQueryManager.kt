package com.alimert.kotlin.omdb.util

import com.alimert.kotlin.omdb.BuildConfig


object ApiQueryManager {

    private val apiQuery = hashMapOf("apiKey" to BuildConfig.API_KEY,
                            "plot" to "short",
                            "type" to "movie",
                            "r" to "json")

    init {}

    fun addQueryParams(key: String, value: String): Map<String, String> {
        val apiQueryMap = HashMap(apiQuery)
        apiQueryMap.put(key, value)
        return apiQueryMap
    }

}