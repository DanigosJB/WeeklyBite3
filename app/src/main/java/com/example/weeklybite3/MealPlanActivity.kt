package com.example.weeklybite3.plan

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weeklybite3.databinding.ActivityMealPlanBinding
import com.google.android.material.snackbar.Snackbar
import android.content.Intent

class MealPlanActivity : AppCompatActivity(), DayAdapter.Callbacks {

    private lateinit var binding: ActivityMealPlanBinding
    private lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Weekly Meal Plan"

        adapter = DayAdapter(PlanRepository.week, this)
        binding.rvDays.layoutManager = LinearLayoutManager(this)
        binding.rvDays.adapter = adapter

        binding.btnMealPlan.setOnClickListener {
            Snackbar.make(binding.root, "You're on Meal plan", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnGrocery.setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }
    }

    override fun onAddMeal(dayPos: Int, type: MealType) {
        val edit = layoutInflater.inflate(com.example.weeklybite3.R.layout.dialog_edit_text, null)
        val input = edit.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.example.weeklybite3.R.id.etInput
        )
        AlertDialog.Builder(this)
            .setTitle("${PlanRepository.week[dayPos].name} • ${type.label}")
            .setView(edit)
            .setPositiveButton("Save") { d, _ ->
                val text = input.text?.toString()?.trim().orEmpty()
                if (text.isNotEmpty()) {
                    PlanRepository.week[dayPos].meals[type] = text
                    adapter.notifyItemChanged(dayPos)
                }
                d.dismiss()
            }
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .show()
    }

    override fun onClearDay(dayPos: Int) {
        PlanRepository.week[dayPos].meals.keys.forEach { PlanRepository.week[dayPos].meals[it] = null }
        adapter.notifyItemChanged(dayPos)
    }
}
