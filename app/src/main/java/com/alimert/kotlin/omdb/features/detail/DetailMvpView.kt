package com.alimert.kotlin.omdb.features.detail

import com.alimert.kotlin.omdb.data.model.omdb.MovieDetail
import com.alimert.kotlin.omdb.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showMovieDetails(movieDetail: MovieDetail)

}