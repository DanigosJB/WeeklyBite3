package com.example.weeklybite3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weeklybite3.databinding.ItemDayBinding
import com.google.android.material.card.MaterialCardView

class DayAdapter(
    private val days: List<DayPlan>,
    private val callbacks: Callbacks
) : RecyclerView.Adapter<DayAdapter.VH>() {

    interface Callbacks {
        fun onAddMeal(dayPos: Int, type: MealType)
        fun onClearDay(dayPos: Int)
    }

    inner class VH(val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val binding = holder.binding
        val day = days[position]

        // Day name
        binding.tvDayName.text = day.name

        // Clear button
        binding.tvClear.setOnClickListener {
            callbacks.onClearDay(holder.adapterPosition)
        }

        // Helper to wire each meal card
        fun bindMeal(
            card: MaterialCardView,
            valueView: android.widget.TextView,
            mealType: MealType
        ) {
            // Label text from enum (Breakfast, Lunch, Dinner)
            // if you prefer static text, leave your XML labels instead
            when (mealType) {
                MealType.Breakfast -> binding.tvBreakfast.text = mealType.label
                MealType.Lunch -> binding.tvLunch.text = mealType.label
                MealType.Dinner -> binding.tvDinner.text = mealType.label
            }

            val value = day.meals[mealType]
            if (value.isNullOrBlank()) {
                valueView.text = "Add meal"
                valueView.setTextColor(0xFF999999.toInt())
            } else {
                valueView.text = value
                valueView.setTextColor(0xFF444444.toInt())
            }

            card.setOnClickListener {
                callbacks.onAddMeal(holder.adapterPosition, mealType)
            }
        }

        // Bind the three meal “pills”
        bindMeal(binding.cardBreakfast, binding.tvBreakfastValue, MealType.Breakfast)
        bindMeal(binding.cardLunch, binding.tvLunchValue, MealType.Lunch)
        bindMeal(binding.cardDinner, binding.tvDinnerValue, MealType.Dinner)
    }
}
