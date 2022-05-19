package com.example.projectexam

import com.example.projectexam.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class RawGames : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.create().apply {
            inject(
                this@RawGames
            )
        }
    }
}