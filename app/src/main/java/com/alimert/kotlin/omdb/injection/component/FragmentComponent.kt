package com.alimert.kotlin.omdb.injection.component

import com.alimert.kotlin.omdb.injection.PerFragment
import com.alimert.kotlin.omdb.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent