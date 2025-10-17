package com.example.weeklybite3.plan

import java.util.UUID

enum class MealType(val label: String) { Breakfast("Breakfast"), Lunch("Lunch"), Dinner("Dinner") }

data class DayPlan(
    val name: String,
    val meals: MutableMap<MealType, String?> = mutableMapOf(
        MealType.Breakfast to null,
        MealType.Lunch to null,
        MealType.Dinner to null
    )
)

data class GroceryItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    var bought: Boolean = false
)

/** Simple in-memory store so both screens share state */
object PlanRepository {
    val week: MutableList<DayPlan> = mutableListOf(
        DayPlan("Monday"), DayPlan("Tuesday"), DayPlan("Wednesday"),
        DayPlan("Thursday"), DayPlan("Friday"), DayPlan("Saturday"), DayPlan("Sunday")
    )
    val groceries: MutableList<GroceryItem> = mutableListOf()
}
