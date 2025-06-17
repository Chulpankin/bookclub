package com.itis.bookclub.presentation.list

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.itis.bookclub.databinding.ItemLoadingBinding

class LoadStateViewHolder(
    private val binding: ItemLoadingBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
    }
}