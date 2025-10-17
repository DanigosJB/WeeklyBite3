package com.example.weeklybite3.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weeklybite3.databinding.ItemGroceryBinding

class GroceryAdapter(
    private val items: List<GroceryItem>,
    private val callbacks: Callbacks
) : RecyclerView.Adapter<GroceryAdapter.VH>() {

    interface Callbacks { fun onToggleBought(position: Int) }

    inner class VH(val b: ItemGroceryBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.b.tvName.text = item.name
        holder.b.cbBought.isChecked = item.bought
        holder.b.cbBought.setOnCheckedChangeListener { _, _ ->
            callbacks.onToggleBought(holder.adapterPosition)
        }
    }
}
