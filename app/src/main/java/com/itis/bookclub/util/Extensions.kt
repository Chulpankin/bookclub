package com.itis.bookclub.util

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.itis.bookclub.BookClubApplication
import com.itis.bookclub.di.AppComponent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.observe(
    fragmentLifecycleOwner: LifecycleOwner,
    crossinline block: (T) -> Unit
): Job {
    return fragmentLifecycleOwner.lifecycleScope.launch {
        fragmentLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            collect { data -> block(data) }
        }
    }
}

inline fun <R> runSuspendCatching(
    exceptionHandler: AppExceptionHandler,
    block: () -> R
): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(exceptionHandler.handleException(e))
    }
}

@Suppress("RecursivePropertyAccessor")
val Context.appComponent: AppComponent
    get() = when (this) {
        is BookClubApplication -> appComponent
        else -> this.applicationContext.appComponent
    }