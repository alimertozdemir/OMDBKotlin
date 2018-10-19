package com.alimert.kotlin.omdb.features.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alimert.kotlin.omdb.R
import com.alimert.kotlin.omdb.data.model.omdb.Movie
import com.alimert.kotlin.omdb.data.model.omdb.MovieDetail
import com.alimert.kotlin.omdb.features.base.BaseActivity
import com.alimert.kotlin.omdb.features.common.ErrorView
import com.alimert.kotlin.omdb.util.extensions.getFormattedString
import com.alimert.kotlin.omdb.util.extensions.gone
import com.alimert.kotlin.omdb.util.extensions.loadImageFromUrl
import com.alimert.kotlin.omdb.util.extensions.visible
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailMvpView, ErrorView.ErrorListener {

    @Inject lateinit var detailPresenter: DetailPresenter

    private var imdbId: String = ""

    companion object {
        const val MOVIE = "MOVIE"

        fun getStartIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE, movie)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        detailPresenter.attachView(this)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewError.setErrorListener(this)

        this.getMovieDetail()
    }

    override fun layoutId() = R.layout.activity_detail

    override fun showMovieDetails(movieDetail: MovieDetail) {

        Timber.d("MOVIE DETAIL: " + movieDetail.imdbID)

        // Kotlin extension
        tvReleaseDate.text = getFormattedString(R.string.release_date, movieDetail.released)
        tvRated.text = getFormattedString(R.string.rated, movieDetail.rated)
        tvRuntime.text = getFormattedString(R.string.runtime, movieDetail.runtime)
        tvGenre.text = getFormattedString(R.string.genre, movieDetail.genre)
        tvDirector.text = getFormattedString(R.string.director, movieDetail.director)
        tvWriter.text = getFormattedString(R.string.writer, movieDetail.writer)
        tvActors.text = getFormattedString(R.string.actors, movieDetail.actors)
        tvPlot.text = getFormattedString(R.string.plot, movieDetail.plot)
        tvLanguage.text = getFormattedString(R.string.language, movieDetail.language)
        tvCountry.text = getFormattedString(R.string.country, movieDetail.country)
        tvAwards.text = getFormattedString(R.string.awards, movieDetail.awards)
    }

    override fun showProgress(show: Boolean) {
        viewError?.gone()
        progress?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(error: Throwable) {
        nested_scroll?.gone()
        viewError?.visible()
        Timber.e(error, "There was a problem retrieving the pokemon...")
    }

    override fun onReloadData() {
        detailPresenter.getMovieDetail(imdbId)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter.detachView()
    }

    private fun getMovieDetail() {

        val movie = intent.getSerializableExtra(MOVIE) as? Movie
                ?: throw IllegalArgumentException("Detail Activity requires a @movieId")

        val title = movie.title
        val movieId = movie.imdbID
        val poster = movie.poster

        if (title != null) {
            collapsingToolbar.title = title
        }

        if (movieId != null) {
            detailPresenter.getMovieDetail(movieId)
        }

        if (poster != null) {
            // TOOD: ImageView Kotlin Extension Sample
            ivPoster.loadImageFromUrl(poster)
            /*Glide.with(this)
                    .load(poster)
                    .apply(RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_launcher_background)
                            .priority(Priority.HIGH)
                    )
                    .into(ivPoster)*/
        }
    }
}