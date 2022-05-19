package com.example.projectexam.di.builder

import androidx.lifecycle.ViewModelProvider
import com.example.projectexam.di.factory.ViewModelFactory
import com.example.projectexam.di.module.HomeModule
import com.example.projectexam.di.module.SearchModule
import com.example.projectexam.di.scope.Presentation
import com.example.projectexam.presentation.activity.HomeActivity
import com.example.projectexam.presentation.activity.SearchActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityBuilder {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Presentation
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @Presentation
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchActivity(): SearchActivity
}