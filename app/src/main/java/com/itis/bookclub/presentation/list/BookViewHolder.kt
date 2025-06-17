package com.itis.bookclub.presentation.list


import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.itis.bookclub.databinding.ItemBookBinding
import com.itis.bookclub.presentation.model.BookUiModel

class BookViewHolder(
    private val viewBinding: ItemBookBinding,
    private val onItemClicked: (String) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(item: BookUiModel) {
        viewBinding.tvBookTitle.text = item.title
        viewBinding.tvBookYear.text = item.publishedYear
        viewBinding.tvBookAuthors.text = item.authorNames
        viewBinding.ivBookCover.load(data = item.coverUrl)

        viewBinding.root.setOnClickListener { onItemClicked(item.id) }
    }
}
