package com.alimert.kotlin.omdb.data.model.omdb

import com.squareup.moshi.Json
import java.io.Serializable

class Movie: Serializable {
    @Json(name="Title")
    var title: String? = null
    @Json(name="Year")
    var year: String? = null
    @Json(name="imdbID")
    var imdbID: String? = null
    @Json(name= "Type")
    var type: String? = null
    @Json(name="Poster")
    var poster: String? = null
}