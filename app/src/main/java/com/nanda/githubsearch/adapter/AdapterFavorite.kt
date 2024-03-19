package com.nanda.githubsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nanda.githubsearch.database.Favorite
import com.nanda.githubsearch.databinding.ItemUserlistBinding

class AdapterFavorite(private val onItemClick: OnClickListener?) :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

    interface OnClickListener {
        fun itemClick(data: Favorite)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun sendCategory(value: List<Favorite>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserlistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemUserlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.itemClick(differ.currentList[adapterPosition])
            }
        }

        fun bind(data: Favorite) {
            with(binding) {
                Glide.with(ivItem)
                    .load(data.avatarUrl)
                    .fitCenter()
                    .into(ivItem)
                tvUsername.text = data.username
            }
        }
    }
}
