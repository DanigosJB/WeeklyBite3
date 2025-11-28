package com.example.weeklybite3

// Which meal a slot represents
enum class MealType(val label: String) {
    Breakfast("Breakfast"),
    Lunch("Lunch"),
    Dinner("Dinner")
}

// One day's plan: name + map of meals (Breakfast/Lunch/Dinner → text or null)
data class DayPlan(
    val name: String,
    val meals: MutableMap<MealType, String?> = mutableMapOf(
        MealType.Breakfast to null,
        MealType.Lunch to null,
        MealType.Dinner to null
    )
)

// In-memory week plan. You can later load this from a DB / API.
object PlanRepository {
    val week: MutableList<DayPlan> = mutableListOf(
        DayPlan("Monday"),
        DayPlan("Tuesday"),
        DayPlan("Wednesday"),
        DayPlan("Thursday"),
        DayPlan("Friday"),
        DayPlan("Saturday"),
        DayPlan("Sunday")
    )
}
