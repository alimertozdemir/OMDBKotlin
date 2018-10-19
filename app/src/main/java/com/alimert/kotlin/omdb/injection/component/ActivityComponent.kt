package com.alimert.kotlin.omdb.injection.component

import com.alimert.kotlin.omdb.injection.PerActivity
import com.alimert.kotlin.omdb.injection.module.ActivityModule
import com.alimert.kotlin.omdb.features.base.BaseActivity
import com.alimert.kotlin.omdb.features.detail.DetailActivity
import com.alimert.kotlin.omdb.features.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)
}
