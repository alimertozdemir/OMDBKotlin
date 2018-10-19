package com.alimert.kotlin.omdb.features.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.alimert.kotlin.omdb.R
import com.alimert.kotlin.omdb.data.model.omdb.Movie
import javax.inject.Inject

class MovieAdapter @Inject
constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = arrayListOf()
    private var clickListener: ClickListener? = null
    private var context: Context? = null

    init {
        movieList = emptyList<Movie>()
    }

    fun setMovies(movies: List<Movie>) {
        movieList = movies
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        this.context = parent.context
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface ClickListener {
        fun onMovieClick(movie: Movie)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var selectedMovie: Movie

        @BindView(R.id.tv_title)
        @JvmField var title: TextView? = null
        @BindView(R.id.tv_year)
        @JvmField var year: TextView? = null
        @BindView(R.id.iv_thumbnail)
        @JvmField var thumbnail: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {
                clickListener?.onMovieClick(selectedMovie)
            }
        }

        fun bind(movie: Movie) {
            selectedMovie = movie
            title?.text = String.format("%s%s", movie.title?.substring(0, 1)?.toUpperCase(),
                    movie.title?.substring(1))
            year?.text = movie.year
            Glide.with(context!!)
                    .load(movie.poster)
                    .apply(RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .priority(Priority.HIGH))
                    .into(thumbnail!!)
        }
    }

}