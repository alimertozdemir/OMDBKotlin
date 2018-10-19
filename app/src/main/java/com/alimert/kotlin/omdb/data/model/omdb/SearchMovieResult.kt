package com.alimert.kotlin.omdb.data.model.omdb

import com.squareup.moshi.Json

class SearchMovieResult{
        @Json(name="Search")
        var search: List<Movie> = arrayListOf(Movie())
        @Json(name="totalResults")
        var totalResults: String? = null
        @Json(name="Response")
        var response: String? = null
        @Json(name="Error")
        var error: String? = null
}