package com.alimert.kotlin.omdb.features.detail

import com.alimert.kotlin.omdb.data.DataManager
import com.alimert.kotlin.omdb.data.model.omdb.MovieDetail
import com.alimert.kotlin.omdb.features.base.BasePresenter
import com.alimert.kotlin.omdb.injection.ConfigPersistent
import com.alimert.kotlin.omdb.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class DetailPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<DetailMvpView>() {

    fun getMovieDetail(imdbId: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getMovieDetail(imdbId)
                .compose(SchedulerUtils.ioToMain<MovieDetail>())
                .subscribe({ movieDetail ->
                    mvpView?.apply {
                        showProgress(false)
                        if (success.equals(movieDetail.response)) {
                            showMovieDetails(movieDetail)
                        } else {
                            showError(Throwable(movieDetail.error))
                        }
                    }
                }) { throwable ->
                    mvpView?.apply {
                        showProgress(false)
                        showError(throwable)
                    }
                }

    }
}