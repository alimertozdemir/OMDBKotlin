package com.alimert.kotlin.omdb.features.main

import com.alimert.kotlin.omdb.data.model.omdb.Movie
import com.alimert.kotlin.omdb.features.base.MvpView

interface MainMvpView : MvpView {

    fun showMovieResult(movies: List<Movie>)

}