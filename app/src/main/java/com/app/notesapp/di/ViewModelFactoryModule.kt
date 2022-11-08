package com.app.notesapp.di

import androidx.lifecycle.ViewModelProvider
import com.app.notesapp.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    // Method #2
    @Binds
    abstract fun bindViewModelFactory(viewModelProvideFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}