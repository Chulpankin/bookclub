package com.itis.bookclub.presentation.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.itis.bookclub.di.keys.ViewModelKey
import com.itis.bookclub.presentation.MainActivity
import com.itis.bookclub.presentation.librarylist.LibraryListViewModel
import com.itis.bookclub.presentation.signin.SignInViewModel
import com.itis.bookclub.presentation.signup.SignUpViewModel
import com.itis.bookclub.presentation.list.BookListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class PresentationModule {

    @Provides
    @Singleton
    fun provideNavController(context: Context): NavController? {
        return (context as MainActivity).navController
    }

    @[Provides IntoMap ViewModelKey(BookListViewModel::class)]
    fun provideBookListViewModel(viewModel: BookListViewModel): ViewModel = viewModel

    @[Provides IntoMap ViewModelKey(LibraryListViewModel::class)]
    fun provideLibraryListViewModel(viewModel: LibraryListViewModel): ViewModel = viewModel

    @[Provides IntoMap ViewModelKey(SignInViewModel::class)]
    fun provideSignInViewModel(viewModel: SignInViewModel): ViewModel = viewModel

    @[Provides IntoMap ViewModelKey(SignUpViewModel::class)]
    fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel = viewModel
}