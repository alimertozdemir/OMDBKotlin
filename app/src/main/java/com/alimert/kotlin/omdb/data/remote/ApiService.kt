package com.alimert.kotlin.omdb.data.remote

import com.alimert.kotlin.omdb.data.model.omdb.MovieDetail
import com.alimert.kotlin.omdb.data.model.omdb.SearchMovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET(".")
    fun getSearchResult(@QueryMap options: Map<String, String>): Single<SearchMovieResult>

    @GET(".")
    fun getMovieDetail(@QueryMap options: Map<String, String>): Single<MovieDetail>

}
