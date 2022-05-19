package com.example.projectexam.di.component

import com.example.projectexam.RawGames
import com.example.projectexam.di.builder.ActivityBuilder
import com.example.projectexam.di.module.ApplicationModule
import com.example.projectexam.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        ActivityBuilder::class
    ]
)
interface ApplicationComponent : AndroidInjector<RawGames>