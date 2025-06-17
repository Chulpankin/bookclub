package com.itis.bookclub.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
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
import com.itis.bookclub.databinding.FragmentBookListBinding
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.presentation.details.BookDetailsFragment.Companion.BOOK_ID
import com.itis.bookclub.util.appComponent
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListFragment : BaseFragment() {

    @Inject
    lateinit var factory: Lazy<ViewModelProvider.Factory>

    private val viewModel: BookListViewModel by viewModels { factory.get() }

    private val viewBinding: FragmentBookListBinding by viewBinding(FragmentBookListBinding::bind)

    private var adapter: BookPagingAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_book_list
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookPagingAdapter(onItemClicked = ::onBookClicked)
        viewBinding.rvBooks.adapter = adapter?.withLoadStateFooter(
            footer = PagingLoadStateAdapter { adapter?.retry() }
        )

        viewBinding.rvBooks.layoutManager = LinearLayoutManager(context)

        viewBinding.swipeRefresh.setOnRefreshListener {
            adapter?.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter?.loadStateFlow?.collect { loadState ->
                    val isSwipeRefreshing = loadState.source.refresh is LoadState.Loading &&
                            adapter?.itemCount == 0
                    viewBinding.swipeRefresh.isRefreshing = isSwipeRefreshing
                }
            }
        }

        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.setQuery(it.trim()) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        observe()
    }

    private fun onBookClicked(bookId: String) {
        findNavController()
            .navigate(
                R.id.action_bookListFragment_to_bookDetailsFragment,
                bundleOf(BOOK_ID to bookId)
            )
    }

    private fun observe() {
        with(viewModel) {
            lifecycleScope.launch {
                books.collectLatest { pagingData ->
                    adapter?.submitData(pagingData)
                }
            }

            isLoading.observe {
                setLoadingVisibility(it)
            }
        }
    }


    private fun setLoadingVisibility(isVisible: Boolean) {
        viewBinding.loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
