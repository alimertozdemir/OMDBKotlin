package com.alimert.kotlin.omdb.injection.module

import com.alimert.kotlin.omdb.data.remote.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun providePokemonApi(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
}