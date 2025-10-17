package com.example.weeklybite3.plan

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weeklybite3.databinding.ActivityGroceryBinding
import com.google.android.material.snackbar.Snackbar
import android.content.Intent

class GroceryListActivity : AppCompatActivity(), GroceryAdapter.Callbacks {

    private lateinit var binding: ActivityGroceryBinding
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Grocery List"

        adapter = GroceryAdapter(PlanRepository.groceries, this)
        binding.rvGroceries.layoutManager = LinearLayoutManager(this)
        binding.rvGroceries.adapter = adapter

        binding.fabAdd.setOnClickListener { showAddDialog() }

        binding.btnMealPlan.setOnClickListener {
            startActivity(Intent(this, MealPlanActivity::class.java))
        }
        binding.btnGrocery.setOnClickListener {
            Snackbar.make(binding.root, "You're on Grocery List", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showAddDialog() {
        val edit = layoutInflater.inflate(com.example.weeklybite3.R.layout.dialog_edit_text, null)
        val input = edit.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.example.weeklybite3.R.id.etInput
        )
        AlertDialog.Builder(this)
            .setTitle("Add grocery item")
            .setView(edit)
            .setPositiveButton("Add") { d, _ ->
                val text = input.text?.toString()?.trim().orEmpty()
                if (text.isNotEmpty()) {
                    PlanRepository.groceries.add(GroceryItem(name = text))
                    adapter.notifyItemInserted(PlanRepository.groceries.lastIndex)
                }
                d.dismiss()
            }
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .show()
    }

    override fun onToggleBought(position: Int) {
        PlanRepository.groceries[position].bought = !PlanRepository.groceries[position].bought
        adapter.notifyItemChanged(position)
    }
}
