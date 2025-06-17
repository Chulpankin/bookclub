package com.itis.bookclub.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.itis.bookclub.R
import com.itis.bookclub.databinding.FragmentBookDetailsBinding
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.presentation.model.BookDetailsUiModel
import com.itis.bookclub.util.appComponent
import javax.inject.Inject

class BookDetailsFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: BookDetailsViewModel.Factory

    private val viewModel: BookDetailsViewModel by viewModels {
        BookDetailsViewModel.provideFactory(
            assistedFactory = vmFactory,
            bookId = requireArguments().getString(BOOK_ID).orEmpty()
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_book_details
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    private val viewBinding: FragmentBookDetailsBinding
            by viewBinding(FragmentBookDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewBinding.statusToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.status_planned -> viewModel.setStatus(BookStatus.PLANNED)
                    R.id.status_reading -> viewModel.setStatus(BookStatus.READING)
                    R.id.status_finished -> viewModel.setStatus(BookStatus.FINISHED)
                }
            }
        }

        observe()
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        viewBinding.loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        viewBinding.error.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setData(data: BookDetailsUiModel) {
        viewBinding.run {
            bookCover.load(data = data.coverUrl)
            bookTitle.text = data.title
            bookDescription.text = data.description
        }
    }

    private fun observe() {
        with(viewModel) {
            book.observe {
                it?.let { data -> setData(data) }
            }

            isLoading.observe {
                setLoadingVisibility(isVisible = it)
            }

            isError.observe {
                setErrorVisibility(isVisible = it)
            }

            status.observe {
                when (it) {
                    BookStatus.PLANNED -> viewBinding.statusToggleGroup.check(R.id.status_planned)
                    BookStatus.READING -> viewBinding.statusToggleGroup.check(R.id.status_reading)
                    BookStatus.FINISHED -> viewBinding.statusToggleGroup.check(R.id.status_finished)
                    null -> viewBinding.statusToggleGroup.clearChecked()
                }
            }
        }
    }

    companion object {
        const val BOOK_ID = "BOOK_ID"

        fun newInstance(id: String) =
            BookDetailsFragment().apply {
                arguments = bundleOf(BOOK_ID to id)
            }
    }
 }