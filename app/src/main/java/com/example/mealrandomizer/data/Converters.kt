package com.example.mealrandomizer.data

import androidx.room.TypeConverter
import java.util.Date

object Converters {
    // Difficulty converters
    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String = difficulty.name

    @TypeConverter
    fun toDifficulty(name: String): Difficulty = Difficulty.valueOf(name)

    // Date converters (store as Long timestamp)
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // Category list converters
    @TypeConverter
    fun fromCategories(categories: List<Category>): String {
        return categories.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toCategories(data: String): List<Category> {
        return if (data.isEmpty()) emptyList()
        else data.split(",").map { Category.valueOf(it) }
    }

    // Single Category converter (for query parameters)
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)
}