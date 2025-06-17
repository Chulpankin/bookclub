package com.itis.bookclub.presentation.librarylist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.bookclub.R
import com.itis.bookclub.databinding.FragmentLibraryListBinding
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.presentation.details.BookDetailsFragment.Companion.BOOK_ID
import com.itis.bookclub.presentation.list.PagingLoadStateAdapter
import com.itis.bookclub.util.appComponent
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LibraryListFragment : BaseFragment() {

    @Inject
    lateinit var factory: Lazy<ViewModelProvider.Factory>

    private val viewModel: LibraryListViewModel by viewModels { factory.get() }

    private val viewBinding: FragmentLibraryListBinding
        by viewBinding(FragmentLibraryListBinding::bind)

    private var adapter: LibraryBooksPagingAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_library_list

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LibraryBooksPagingAdapter(onItemClicked = ::onBookClicked)
        viewBinding.rvBooks.layoutManager = LinearLayoutManager(context)
        viewBinding.rvBooks.adapter = adapter?.withLoadStateFooter(
            footer = PagingLoadStateAdapter { adapter?.retry() }
        )

        viewBinding.swipeRefresh.setOnRefreshListener {
            adapter?.refresh()
        }

        viewBinding.statusToggleGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            val status = when (checkedId) {
                R.id.status_planned -> BookStatus.PLANNED
                R.id.status_reading -> BookStatus.READING
                R.id.status_finished -> BookStatus.FINISHED
                else -> null
            }
            viewModel.setStatusFilter(status)
            adapter?.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter?.loadStateFlow?.collectLatest { loadState ->
                    val isRefreshing = loadState.source.refresh is LoadState.Loading &&
                            adapter?.itemCount == 0
                    viewBinding.swipeRefresh.isRefreshing = isRefreshing

                    val isEmpty = loadState.source.refresh is LoadState.NotLoading &&
                            adapter?.itemCount == 0
                    viewBinding.tvEmptyList.visibility = if (isEmpty) View.VISIBLE else View.GONE
                }
            }
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collectLatest {
                    adapter?.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest {
                viewBinding.loading.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun onBookClicked(bookId: String) {
        findNavController().navigate(
            R.id.action_libraryListFragment_to_bookDetailsFragment,
            Bundle().apply { putString(BOOK_ID, bookId) }
        )
    }
}
