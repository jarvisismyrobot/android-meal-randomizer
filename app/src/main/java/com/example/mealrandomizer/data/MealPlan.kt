package com.example.mealrandomizer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

/**
 * Represents a generated meal plan for multiple days
 */
@Entity(tableName = "meal_plans")
data class MealPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "Meal Plan",
    
    // Number of days in this plan (1-7)
    val days: Int = 7,
    
    // When this plan was generated
    @TypeConverters(Converters::class)
    val generatedAt: Date = Date(),
    
    // Start date of the plan (optional, could be today)
    @TypeConverters(Converters::class)
    val startDate: Date = Date(),
    
    // Whether to avoid repeating meals within this plan
    val avoidRepeats: Boolean = true
)

/**
 * Represents a single meal entry within a meal plan
 * Links to a specific Meal and specifies day and meal time
 */
@Entity(tableName = "meal_plan_entries")
data class MealPlanEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Reference to the meal plan
    val mealPlanId: Long,
    
    // Reference to the actual meal
    val mealId: Long,
    
    // Day index (0 = first day, 1 = second day, etc.)
    val dayIndex: Int,
    
    // Meal time: 0 = breakfast, 1 = lunch, 2 = dinner
    val mealTime: Int,
    
    // Position within the meal time (0, 1, 2 for 3 dishes per meal)
    val position: Int
)

/**
 * Data class for displaying a day's meals in the UI
 */
data class DayMeals(
    val dayIndex: Int,
    val date: Date,
    val breakfast: List<Meal> = emptyList(),
    val lunch: List<Meal> = emptyList(),
    val dinner: List<Meal> = emptyList()
)

/**
 * Complete meal plan data for UI display
 */
data class MealPlanDisplay(
    val plan: MealPlan,
    val days: List<DayMeals>
)