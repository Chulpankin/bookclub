package com.itis.bookclub.presentation.utils.impl

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.itis.bookclub.R
import com.itis.bookclub.presentation.details.BookDetailsFragment.Companion.BOOK_ID
import com.itis.bookclub.presentation.utils.NavigationController
import com.itis.bookclub.util.Toaster
import javax.inject.Inject


class NavigationControllerImpl @Inject constructor(
    private val navController: NavController?,
    private val toaster: Toaster
) : NavigationController {

    override fun navigateToDetails(data: String) {
        navController?.navigate(R.id.bookDetailsFragment, bundleOf(BOOK_ID to data))
    }

    override fun showAuthError() {
        toaster.showAuthError()
    }
}