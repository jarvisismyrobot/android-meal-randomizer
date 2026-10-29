package com.example.mealrandomizer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM meal_plans ORDER BY generatedAt DESC")
    fun getAll(): Flow<List<MealPlan>>
    
    @Query("SELECT * FROM meal_plans WHERE id = :id")
    suspend fun getById(id: Long): MealPlan?
    
    @Insert
    suspend fun insert(mealPlan: MealPlan): Long
    
    @Update
    suspend fun update(mealPlan: MealPlan)
    
    @Delete
    suspend fun delete(mealPlan: MealPlan)
    
    @Query("DELETE FROM meal_plans")
    suspend fun deleteAll()
    
    @Query("SELECT * FROM meal_plans ORDER BY generatedAt DESC LIMIT 1")
    suspend fun getLatest(): MealPlan?
    
    // Get all entries for a specific meal plan
    @Query("SELECT * FROM meal_plan_entries WHERE mealPlanId = :mealPlanId ORDER BY dayIndex, mealTime, position")
    fun getEntriesForPlan(mealPlanId: Long): Flow<List<MealPlanEntry>>
    
    @Insert
    suspend fun insertEntry(entry: MealPlanEntry): Long
    
    @Insert
    suspend fun insertEntries(entries: List<MealPlanEntry>)
    
    @Query("DELETE FROM meal_plan_entries WHERE mealPlanId = :mealPlanId")
    suspend fun deleteEntriesForPlan(mealPlanId: Long)
    
    // Get meal plan with its entries and meals in one query
    @Transaction
    @Query("SELECT * FROM meal_plans WHERE id = :id")
    suspend fun getPlanWithEntries(id: Long): MealPlanWithEntries?

    // Reactive version: re-emits whenever the plan OR its entries change
    // (Room tracks all tables in the @Transaction, so entry updates refresh the UI)
    @Transaction
    @Query("SELECT * FROM meal_plans ORDER BY generatedAt DESC LIMIT 1")
    fun getLatestPlanWithEntriesFlow(): Flow<MealPlanWithEntries?>

    // Delete one meal slot (a single day + meal time), used by "shuffle this meal"
    @Query("DELETE FROM meal_plan_entries WHERE mealPlanId = :planId AND dayIndex = :dayIndex AND mealTime = :mealTime")
    suspend fun deleteSlotEntries(planId: Long, dayIndex: Int, mealTime: Int)
    
    // Check if a meal is used in any meal plan
    @Query("SELECT COUNT(*) FROM meal_plan_entries WHERE mealId = :mealId")
    suspend fun countMealUsage(mealId: Long): Int
}

data class MealPlanWithEntries(
    @Embedded val mealPlan: MealPlan,
    @Relation(
        entity = MealPlanEntry::class,
        parentColumn = "id",
        entityColumn = "mealPlanId"
    )
    val entries: List<MealPlanEntryWithMeal>
)

data class MealPlanEntryWithMeal(
    @Embedded val entry: MealPlanEntry,
    @Relation(
        parentColumn = "mealId",
        entityColumn = "id"
    )
    val meal: Meal
)