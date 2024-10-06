package com.example.elearning.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elearning.databinding.MainItemBinding
import com.example.elearning.models.Result

class MainRVAdapter(
    private val itemList: List<Result>,
    private val taskClickListener: OnItemClickListener
) : RecyclerView.Adapter<MainRVAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(course: Result)
    }

    class ItemViewHolder(
        private val binding: MainItemBinding,
        private val taskClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$${item.price}"
            binding.root.setOnClickListener {
                taskClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, taskClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
