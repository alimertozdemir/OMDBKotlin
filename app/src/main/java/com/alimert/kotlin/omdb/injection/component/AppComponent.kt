package com.alimert.kotlin.omdb.injection.component

import android.app.Application
import android.content.Context
import com.alimert.kotlin.omdb.data.DataManager
import com.alimert.kotlin.omdb.data.remote.ApiService
import com.alimert.kotlin.omdb.injection.ApplicationContext
import com.alimert.kotlin.omdb.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun pokemonApi(): ApiService
}
