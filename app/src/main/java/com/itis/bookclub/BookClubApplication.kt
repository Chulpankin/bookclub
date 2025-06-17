package com.itis.bookclub

import android.app.Application
import com.itis.bookclub.di.AppComponent
import com.itis.bookclub.di.DaggerAppComponent

class BookClubApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .provideContext(ctx = this)
            .build()
    }
}