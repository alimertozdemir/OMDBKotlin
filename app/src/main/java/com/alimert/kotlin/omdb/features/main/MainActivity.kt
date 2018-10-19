package com.alimert.kotlin.omdb.features.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.alimert.kotlin.omdb.R
import com.alimert.kotlin.omdb.data.model.omdb.Movie
import com.alimert.kotlin.omdb.features.base.BaseActivity
import com.alimert.kotlin.omdb.features.common.ErrorView
import com.alimert.kotlin.omdb.features.detail.DetailActivity
import com.alimert.kotlin.omdb.features.main.adapter.MovieAdapter
import com.alimert.kotlin.omdb.util.extensions.gone
import com.alimert.kotlin.omdb.util.view.RecyclerItemDecoration
import com.alimert.kotlin.omdb.util.extensions.visible
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, MovieAdapter.ClickListener, ErrorView.ErrorListener, SearchView.OnQueryTextListener {

    @Inject lateinit var movieAdapter: MovieAdapter
    @Inject lateinit var mainPresenter: MainPresenter

    private var searchKey: String = ""
    private val MIN_SEARCH_KEY_LENGTH = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(toolbar)
        swipeToRefresh?.apply {
            setProgressBackgroundColorSchemeResource(R.color.primary)
            setColorSchemeResources(R.color.white)
            setOnRefreshListener { mainPresenter.getSearchResult(searchKey) }
        }

        movieAdapter.setClickListener(this)
        recyclerViewMovies?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            addItemDecoration(RecyclerItemDecoration(context))
        }

        viewError?.setErrorListener(this)
    }

    override fun layoutId() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun showMovieResult(movies: List<Movie>) {
        movieAdapter.apply {
            setMovies(movies)
            notifyDataSetChanged()
        }

        recyclerViewMovies?.visible()
        swipeToRefresh?.visible()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (recyclerViewMovies?.visibility == View.VISIBLE && movieAdapter.itemCount > 0) {
                swipeToRefresh?.isRefreshing = true
            } else {
                progressBar?.visible()
                recyclerViewMovies?.gone()
                swipeToRefresh?.gone()
            }

            viewError?.gone()
        } else {
            swipeToRefresh?.isRefreshing = false
            progressBar?.gone()
        }
    }

    override fun showError(error: Throwable) {
        recyclerViewMovies?.gone()
        swipeToRefresh?.gone()
        viewError?.visible()
        Timber.e(error, "There was an error retrieving the movie")
    }

    override fun onMovieClick(movie: Movie) {
        Timber.d("CLICKED MOVIE NAME IS : " + movie.title)
        startActivity(DetailActivity.getStartIntent(this, movie))
    }

    override fun onReloadData() {
        mainPresenter.getSearchResult(searchKey)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager = this@MainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView? = null

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(queryText: String?): Boolean {
        var searchKey = queryText?.trim { it <= ' ' }
        if (searchKey.isNullOrBlank() || searchKey!!.length < MIN_SEARCH_KEY_LENGTH) {
            return false
        }

        mainPresenter.getSearchResult(searchKey)
        return false
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

}