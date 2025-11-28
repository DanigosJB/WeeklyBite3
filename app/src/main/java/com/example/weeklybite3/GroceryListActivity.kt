package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weeklybite3.databinding.ActivityGroceryBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class GroceryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroceryBinding
    private lateinit var adapter: GroceryAdapter

    private val items: MutableList<GroceryItem>
        get() = GroceryRepository.items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.title_activity_grocery_list)

        // RecyclerView + adapter
        adapter = GroceryAdapter(
            items = items,
            onItemChecked = { pos ->
                // You can do something here if you want, e.g. Snackbar
                // Snackbar.make(binding.root, "Toggled ${items[pos].name}", Snackbar.LENGTH_SHORT).show()
            },
            onItemDelete = { pos ->
                val removed = items.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                updateEmptyState()
                Snackbar.make(
                    binding.root,
                    "Removed ${removed.name}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        )

        binding.rvGroceries.layoutManager = LinearLayoutManager(this)
        binding.rvGroceries.adapter = adapter

        // Bottom nav buttons
        binding.btnMealPlan.setOnClickListener {
            startActivity(Intent(this, MealPlanActivity::class.java))
            finish()
        }

        binding.btnGrocery.setOnClickListener {
            Snackbar.make(binding.root, "You're already on Grocery List", Snackbar.LENGTH_SHORT)
                .show()
        }

        // FAB: add new item
        binding.fabAdd.setOnClickListener {
            showAddItemDialog()
        }

        updateEmptyState()
    }

    private fun updateEmptyState() {
        if (items.isEmpty()) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvGroceries.visibility = View.GONE
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvGroceries.visibility = View.VISIBLE
        }
    }

    private fun showAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_text, null)
        val input = dialogView.findViewById<TextInputEditText>(R.id.etInput)

        AlertDialog.Builder(this)
            .setTitle("Add grocery item")
            .setView(dialogView)
            .setPositiveButton("Add") { d, _ ->
                val text = input.text?.toString()?.trim().orEmpty()
                if (text.isNotEmpty()) {
                    items.add(GroceryItem(name = text))
                    adapter.notifyItemInserted(items.lastIndex)
                    updateEmptyState()
                }
                d.dismiss()
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .show()
    }
}
