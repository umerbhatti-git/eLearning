package com.example.elearning.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elearning.databinding.OrderConfirmItemBinding
import com.example.elearning.models.Result

class OrderConfirmRVAdapter(
    private val itemList: List<Result>
) : RecyclerView.Adapter<OrderConfirmRVAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        private val binding: OrderConfirmItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result, pos: Int) {
            binding.tvCount.text = "${pos})"
            binding.tvTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            OrderConfirmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, holder.bindingAdapterPosition + 1)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}