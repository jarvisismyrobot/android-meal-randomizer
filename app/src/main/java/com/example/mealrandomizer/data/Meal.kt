package com.example.mealrandomizer.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "meals",
    indices = [Index(value = ["name"], unique = true)]
)
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val cookingTimeMinutes: Int,
    val calories: Int?,
    @TypeConverters(Converters::class)
    val categories: List<Category>, // comma separated in database
    @TypeConverters(Converters::class)
    val createdAt: Date = Date(),
    @TypeConverters(Converters::class)
    val lastGenerated: Date? = null
)

enum class Category {
    BREAKFAST, LUNCH, DINNER, VEGETARIAN, QUICK, HEALTHY, HEARTY, SNACK, DESSERT
}