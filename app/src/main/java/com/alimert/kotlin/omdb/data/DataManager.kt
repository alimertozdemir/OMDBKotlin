package com.alimert.kotlin.omdb.data

import com.alimert.kotlin.omdb.data.model.omdb.MovieDetail
import com.alimert.kotlin.omdb.data.model.omdb.SearchMovieResult
import com.alimert.kotlin.omdb.data.remote.ApiService
import com.alimert.kotlin.omdb.util.ApiQueryManager
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val apiService: ApiService) {

    private var apiQuery: Map<String, String> = hashMapOf()

    fun getSearchResult(searchKey: String): Single<SearchMovieResult> {
        apiQuery = ApiQueryManager.addQueryParams("s", searchKey)
        return apiService.getSearchResult(apiQuery)
    }

    fun getMovieDetail(imdbId: String): Single<MovieDetail> {
        apiQuery = ApiQueryManager.addQueryParams("i", imdbId)
        return apiService.getMovieDetail(apiQuery)
    }

}