package com.example.weeklybite3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weeklybite3.databinding.ItemGroceryBinding

class GroceryAdapter(
    private val items: MutableList<GroceryItem>,
    private val onItemChecked: (Int) -> Unit,
    private val onItemDelete: (Int) -> Unit
) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(val binding: ItemGroceryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroceryBinding.inflate(inflater, parent, false)
        return GroceryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding

        // Basic text fields
        b.tvName.text = item.name
        b.tvCategory.text = item.category
        b.tvQuantity.text = "x${item.quantity}"

        // Helper to apply "done" vs "not done" visuals
        fun applyCheckedState(checked: Boolean) {
            b.ivDone.setImageResource(
                if (checked)
                    android.R.drawable.checkbox_on_background
                else
                    android.R.drawable.checkbox_off_background
            )

            val alpha = if (checked) 0.4f else 1f
            b.tvName.alpha = alpha
            b.tvCategory.alpha = alpha
            b.tvQuantity.alpha = alpha
        }

        applyCheckedState(item.isChecked)

        // Tap the icon to toggle checked state
        b.ivDone.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

            val current = items[pos]
            current.isChecked = !current.isChecked
            applyCheckedState(current.isChecked)
            onItemChecked(pos)
        }

        // Long press on the card to delete
        b.root.setOnLongClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onItemDelete(pos)
            }
            true
        }
    }
}
