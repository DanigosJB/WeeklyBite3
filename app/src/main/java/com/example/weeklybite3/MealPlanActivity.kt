package com.example.weeklybite3.plan

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weeklybite3.LoginSignupActivity
import com.example.weeklybite3.SessionManager
import com.example.weeklybite3.databinding.ActivityMealPlanBinding
import com.google.android.material.snackbar.Snackbar

class MealPlanActivity : AppCompatActivity(), DayAdapter.Callbacks {

    private lateinit var binding: ActivityMealPlanBinding
    private lateinit var adapter: DayAdapter
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + Drawer toggle
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Weekly Meal Plan"

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            com.example.weeklybite3.R.string.navigation_drawer_open,
            com.example.weeklybite3.R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Recycler
        adapter = DayAdapter(PlanRepository.week, this)
        binding.rvDays.layoutManager = LinearLayoutManager(this)
        binding.rvDays.adapter = adapter

        // Bottom buttons
        binding.btnMealPlan.setOnClickListener {
            Snackbar.make(binding.root, "You're on Meal plan", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnGrocery.setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }

        // Drawer item clicks (includes Logout)
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.example.weeklybite3.R.id.nav_plan -> {
                    // already here
                }
                com.example.weeklybite3.R.id.nav_account -> {
                    startActivity(Intent(this, AccountActivity::class.java))
                }
                com.example.weeklybite3.R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                com.example.weeklybite3.R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                com.example.weeklybite3.R.id.nav_logout -> {
                    SessionManager.logout(this)
                    startActivity(Intent(this, LoginSignupActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Let the drawer toggle handle the hamburger click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Using legacy back press for simplicity with DrawerLayout")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
