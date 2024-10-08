package com.example.elearning.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.elearning.R
import com.example.elearning.databinding.MainItemBinding
import com.example.elearning.models.Result

class MainRVAdapter(
    private val itemList: List<Result>,
    private val taskClickListener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<MainRVAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(course: Result)
    }

    class ItemViewHolder(
        private val binding: MainItemBinding,
        private val taskClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result, context: Context) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$${item.price}"
            binding.root.setOnClickListener {
                taskClickListener.onItemClick(item)
            }
            binding.ivFvrt.setOnClickListener {
                binding.ivFvrt.setColorFilter(
                    ContextCompat.getColor(context, R.color.grey),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
                Toast.makeText(context, "The item has been added to favorites", Toast.LENGTH_LONG)
                    .show()
            }

            binding.ivShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                intent.putExtra(Intent.EXTRA_SUBJECT, item.title)
                intent.putExtra(Intent.EXTRA_TEXT, "Details: ${item.details} Price: ${item.price}")
                context.startActivity(Intent.createChooser(intent, "Share Intent"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, taskClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
