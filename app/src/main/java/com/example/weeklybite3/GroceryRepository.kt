package com.example.weeklybite3

/**
 * In-memory grocery data storage.
 * Holds full GroceryItem objects (name, quantity, category, done state).
 */
object GroceryRepository {
    val items: MutableList<GroceryItem> = mutableListOf()
}
