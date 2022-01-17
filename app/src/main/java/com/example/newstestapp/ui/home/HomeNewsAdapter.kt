package com.example.newstestapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newstestapp.databinding.NewsItemBinding
import com.example.newstestapp.model.NewsUiData

class HomeNewsAdapter(val onClickListener: OnClickListener) :
    ListAdapter<NewsUiData, HomeNewsAdapter.NewsFeedViewHolder>(DiffCallback) {

    class NewsFeedViewHolder(private var binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: NewsUiData) {
            binding.viewModel = newsItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsFeedViewHolder {
        return NewsFeedViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsItem)
        }
        holder.bind(newsItem)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsUiData>() {
        override fun areItemsTheSame(oldItem: NewsUiData, newItem: NewsUiData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsUiData, newItem: NewsUiData): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (newsItem: NewsUiData) -> Unit) {
        fun onClick(newsItem: NewsUiData) = clickListener(newsItem)
    }
}