package com.dzakyadlh.storytell.ui.home

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakyadlh.storytell.data.response.ListStoryItem
import com.dzakyadlh.storytell.databinding.ListStoryBinding
import com.dzakyadlh.storytell.ui.detail.DetailActivity

class HomeAdapter : ListAdapter<ListStoryItem, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HomeViewHolder(val binding: ListStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ListStoryItem) {
            with(binding) {
                Glide.with(itemView.context).load(result.photoUrl).into(storyImg)
                storyDesc.text = result.description
                storyName.text = result.name
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_TEXT, result.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewHolder {
        val binding =
            ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

}