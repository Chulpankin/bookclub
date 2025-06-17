package com.itis.bookclub.util

import android.content.Context
import android.widget.Toast
import com.itis.bookclub.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Toaster @Inject constructor(
    private val context: Context,
) {
    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showFromApi() {
        showMessage(context.getString(R.string.loaded_from_api))
    }

    fun showFromCache() {
        showMessage(context.getString(R.string.loaded_from_cache))
    }
    
    fun showAuthError() {
        showMessage(context.getString(R.string.session_is_expired))
        
    }
}
