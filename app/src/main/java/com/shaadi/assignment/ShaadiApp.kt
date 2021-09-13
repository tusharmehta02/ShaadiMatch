package com.shaadi.assignment

import android.app.Application
import com.shaadi.assignment.di.AppComponentInitializer

class ShaadiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponentInitializer.setApplication(this)
    }
}
