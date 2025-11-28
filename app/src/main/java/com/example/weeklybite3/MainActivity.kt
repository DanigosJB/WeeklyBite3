package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weeklybite3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Open Meal Plan
        binding.btnMealPlan.setOnClickListener {
            startActivity(Intent(this, MealPlanActivity::class.java))
        }

        // Open Grocery List
        binding.btnGroceryList.setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }

        // Open Account (and from there they can reach Settings etc.)
        binding.btnAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }
}
