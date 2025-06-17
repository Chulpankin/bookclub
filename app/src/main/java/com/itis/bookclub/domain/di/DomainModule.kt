package com.itis.bookclub.domain.di

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.itis.bookclub.util.AppExceptionHandler
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideExceptionHandler(): AppExceptionHandler = AppExceptionHandler()

    @Provides
    @Singleton
    fun provideNotificationManager(app: Application): NotificationManagerCompat {
        return NotificationManagerCompat.from(app)
    }
}