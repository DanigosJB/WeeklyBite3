package com.example.weeklybite3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weeklybite3.databinding.ItemGroceryBinding

data class GroceryItem(
    val name: String,
    val category: String = "",
    val quantity: Int = 1,
    var done: Boolean = false
)

class GroceryAdapter(
    private val items: MutableList<GroceryItem>,
    private val onItemChecked: (Int) -> Unit,
    private val onItemDelete: (Int) -> Unit
) : RecyclerView.Adapter<GroceryAdapter.VH>() {

    inner class VH(val binding: ItemGroceryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroceryBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val b = holder.binding

        b.tvName.text = item.name
        b.tvCategory.text = if (item.category.isNotBlank()) item.category else "General"
        b.tvQuantity.text = "x${item.quantity}"

        if (item.done) {
            b.ivDone.setImageResource(android.R.drawable.checkbox_on_background)
            b.tvName.alpha = 0.5f
            b.tvCategory.alpha = 0.5f
        } else {
            b.ivDone.setImageResource(android.R.drawable.checkbox_off_background)
            b.tvName.alpha = 1f
            b.tvCategory.alpha = 1f
        }

        b.ivDone.setOnClickListener {
            item.done = !item.done
            notifyItemChanged(position)
            onItemChecked(position)
        }

        holder.itemView.setOnLongClickListener {
            onItemDelete(position)
            true
        }
    }

    override fun getItemCount(): Int = items.size
}
