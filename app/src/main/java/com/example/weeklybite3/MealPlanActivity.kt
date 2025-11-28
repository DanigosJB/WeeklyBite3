package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
        // ✅ Use strings.xml instead of hard-coded text
        supportActionBar?.title = getString(R.string.title_activity_meal_plan)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
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
                R.id.nav_plan -> {
                    // already here
                }
                R.id.nav_account -> {
                    startActivity(Intent(this, AccountActivity::class.java))
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.nav_logout -> {
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

    // Toolbar menu: "Share Plan"
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_meal_plan, menu)
        return true
    }

    // Hamburger + share action
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true

        return when (item.itemId) {
            R.id.action_share -> {
                val planText = buildString {
                    appendLine("My Weekly Meal Plan:")
                    appendLine()
                    PlanRepository.week.forEach { day ->
                        appendLine("${day.name}:")
                        appendLine("  Breakfast: ${day.meals[MealType.Breakfast] ?: "—"}")
                        appendLine("  Lunch:     ${day.meals[MealType.Lunch] ?: "—"}")
                        appendLine("  Dinner:    ${day.meals[MealType.Dinner] ?: "—"}")
                        appendLine()
                    }
                }

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Weekly Meal Plan")
                    putExtra(Intent.EXTRA_TEXT, planText)
                }
                startActivity(Intent.createChooser(shareIntent, "Share using"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Using legacy back press for simplicity with DrawerLayout")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // -------- DayAdapter.Callbacks --------

    override fun onAddMeal(dayPos: Int, type: MealType) {
        val edit = layoutInflater.inflate(R.layout.dialog_edit_text, null)
        val input = edit.findViewById<com.google.android.material.textfield.TextInputEditText>(
            R.id.etInput
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
        PlanRepository.week[dayPos].meals.keys.forEach {
            PlanRepository.week[dayPos].meals[it] = null
        }
        adapter.notifyItemChanged(dayPos)
    }
}
