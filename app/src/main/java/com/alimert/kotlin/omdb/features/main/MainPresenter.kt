package com.alimert.kotlin.omdb.features.main

import com.alimert.kotlin.omdb.data.DataManager
import com.alimert.kotlin.omdb.data.model.omdb.SearchMovieResult
import com.alimert.kotlin.omdb.features.base.BasePresenter
import com.alimert.kotlin.omdb.injection.ConfigPersistent
import com.alimert.kotlin.omdb.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    fun getSearchResult(searchKey: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getSearchResult(searchKey)
                .compose(SchedulerUtils.ioToMain<SearchMovieResult>())
                .subscribe({ result ->
                    mvpView?.apply {
                        showProgress(false)
                        if (success.equals(result.response)) {
                            showMovieResult(result.search)
                        } else {
                            showError(Throwable(result.error))
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