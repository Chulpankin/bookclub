package com.itis.bookclub.presentation.librarylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.bookclub.databinding.ItemLibraryBookBinding
import com.itis.bookclub.presentation.list.LibraryBookViewHolder
import com.itis.bookclub.presentation.model.BookUiModel

class LibraryBooksPagingAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<BookUiModel, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLibraryBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LibraryBookViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as LibraryBookViewHolder).bind(item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BookUiModel>() {
            override fun areItemsTheSame(old: BookUiModel, new: BookUiModel): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: BookUiModel, new: BookUiModel): Boolean =
                old == new
        }
    }
}
