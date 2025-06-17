package com.itis.bookclub.presentation.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.itis.bookclub.R
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.presentation.signup.composable.SignUpScreen
import com.itis.bookclub.presentation.themes.BookClubAppTheme
import com.itis.bookclub.util.DaggerViewModelFactory
import com.itis.bookclub.util.appComponent
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: DaggerViewModelFactory

    private val viewModel: SignUpViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BookClubAppTheme {
                    SignUpScreen(
                        viewModel = viewModel,
                        onNavigateToSignIn = { findNavController().navigate(R.id.signInFragment) }
                    )
                }
            }
        }
    }
}