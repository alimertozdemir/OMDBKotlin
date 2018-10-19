package com.alimert.kotlin.omdb.common.injection.component

import dagger.Component
import com.alimert.kotlin.omdb.common.injection.module.ApplicationTestModule
import com.alimert.kotlin.omdb.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : AppComponent