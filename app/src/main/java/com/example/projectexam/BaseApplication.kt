package com.example.projectexam

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

class BaseApplication {

    @HiltAndroidApp
    class BaseApplication : Application()
}