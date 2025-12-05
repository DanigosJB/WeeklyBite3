package com.example.weeklybite3

// Which meal a card represents
enum class MealType(val label: String) {
    Breakfast("Breakfast"),
    Lunch("Lunch"),
    Dinner("Dinner")
}

// One day in the weekly plan
data class DayPlan(
    val name: String,
    val meals: MutableMap<MealType, String?> = mutableMapOf(
        MealType.Breakfast to null,
        MealType.Lunch to null,
        MealType.Dinner to null
    )
)

// ✅ Single, final version of GroceryItem used everywhere
data class GroceryItem(
    val name: String,
    val category: String = "Misc",
    val quantity: Int = 1,
    var isChecked: Boolean = false
)

// Weekly meal plan repository
object PlanRepository {
    val week = mutableListOf(
        DayPlan("Monday"),
        DayPlan("Tuesday"),
        DayPlan("Wednesday"),
        DayPlan("Thursday"),
        DayPlan("Friday"),
        DayPlan("Saturday"),
        DayPlan("Sunday")
    )
}

// Grocery list repository (shared by UI + adapter)
object GroceryRepository {
    val items = mutableListOf<GroceryItem>()
}
