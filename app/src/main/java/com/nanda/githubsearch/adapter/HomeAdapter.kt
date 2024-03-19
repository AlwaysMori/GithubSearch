package com.nanda.githubsearch.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DiffUtil
import com.nanda.githubsearch.databinding.ItemUserlistBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import com.nanda.githubsearch.network.response.DataItems


class HomeAdapter(private val onItemClick: OnClickListener?) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    interface OnClickListener {
        fun itemClick(data: DataItems)
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataItems>() {
        override fun areItemsTheSame(oldItem: DataItems, newItem: DataItems): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: DataItems, newItem: DataItems): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun sendCategory(value: List<DataItems>) {
        differ.submitList(value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserlistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onItemClick?.itemClick(data)
        }
    }

    inner class ViewHolder(private val binding: ItemUserlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataItems) {
            with(binding) {
                Glide.with(ivItem)
                    .load(data.avatarUrl)
                    .fitCenter()
                    .into(ivItem)
                tvUsername.text = data.login
            }
        }
    }
}
