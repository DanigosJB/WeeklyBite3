package com.example.weeklybite3.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weeklybite3.databinding.ItemDayBinding

class DayAdapter(
    private val items: List<DayPlan>,
    private val callbacks: Callbacks
) : RecyclerView.Adapter<DayAdapter.VH>() {

    interface Callbacks {
        fun onAddMeal(dayPos: Int, type: MealType)
        fun onClearDay(dayPos: Int)
    }

    inner class VH(val b: ItemDayBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val day = items[position]
        val b = holder.b
        b.tvDay.text = day.name

        fun bindSlot(container: View, labelView: TextView, type: MealType) {
            val value = day.meals[type]
            if (value.isNullOrBlank()) {
                labelView.text = type.label
                container.setOnClickListener { callbacks.onAddMeal(position, type) }
            } else {
                labelView.text = value
                container.setOnClickListener(null)
            }
        }

        bindSlot(b.cardBreakfast, b.tvBreakfast, MealType.Breakfast)
        bindSlot(b.cardLunch, b.tvLunch, MealType.Lunch)
        bindSlot(b.cardDinner, b.tvDinner, MealType.Dinner)

        b.btnClear.setOnClickListener { callbacks.onClearDay(position) }
    }
}
