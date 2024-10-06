package com.example.elearning.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elearning.databinding.CartItemBinding
import com.example.elearning.models.Result

class CartRVAdapter(
    private val itemList: List<Result>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CartRVAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(course: Result)
    }

    class ItemViewHolder(
        private val binding: CartItemBinding,
        private val itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$${item.price}"
            binding.ivDel.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
