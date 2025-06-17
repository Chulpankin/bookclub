package com.itis.bookclub.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.itis.bookclub.R
import com.itis.bookclub.databinding.ItemLibraryBookBinding
import com.itis.bookclub.domain.model.BookStatus
import com.itis.bookclub.presentation.model.BookUiModel

class LibraryBookViewHolder(
    private val binding: ItemLibraryBookBinding,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookUiModel) = with(binding) {
        tvBookTitle.text = item.title
        ivBookCover.load(item.coverUrl)

        when (item.status) {
            BookStatus.PLANNED -> {
                tvBookStatus.text = binding.root.context.getString(R.string.planned)
                tvBookStatus.setBackgroundResource(R.drawable.bg_status_planned)
            }
            BookStatus.READING -> {
                tvBookStatus.text = binding.root.context.getString(R.string.reading)
                tvBookStatus.setBackgroundResource(R.drawable.bg_status_reading)
            }
            BookStatus.FINISHED -> {
                tvBookStatus.text = binding.root.context.getString(R.string.finished)
                tvBookStatus.setBackgroundResource(R.drawable.bg_status_finished)
            }
            null -> {
                tvBookStatus.text = ""
                tvBookStatus.visibility = View.GONE
            }
        }

        root.setOnClickListener { onItemClicked(item.id) }
    }
}
