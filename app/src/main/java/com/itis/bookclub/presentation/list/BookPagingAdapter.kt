package com.itis.bookclub.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.bookclub.databinding.ItemBookBinding
import com.itis.bookclub.presentation.model.BookUiModel

class BookPagingAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<BookUiModel, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as BookViewHolder).bindItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onItemClicked)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BookUiModel>() {
            override fun areItemsTheSame(old: BookUiModel, new: BookUiModel) = old.id == new.id
            override fun areContentsTheSame(old: BookUiModel, new: BookUiModel) = old == new
        }
    }
}

