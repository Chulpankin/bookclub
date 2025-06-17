package com.itis.bookclub.di

import androidx.lifecycle.ViewModelProvider
import com.itis.bookclub.presentation.utils.NavigationController
import com.itis.bookclub.presentation.utils.impl.NavigationControllerImpl
import com.itis.bookclub.util.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindDaggerFactoryToViewModelFactory(impl: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun bindNavigationController(impl: NavigationControllerImpl): NavigationController

}