package com.example.mealrandomizer.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealPlanRepository @Inject constructor(
    private val mealPlanDao: MealPlanDao,
    private val mealDao: MealDao
) {
    fun getAllMealPlans(): Flow<List<MealPlan>> = mealPlanDao.getAll()
    
    suspend fun getMealPlan(id: Long): MealPlan? = mealPlanDao.getById(id)
    
    suspend fun saveMealPlan(plan: MealPlan, entries: List<MealPlanEntry>): Long {
        val planId = mealPlanDao.insert(plan)
        entries.forEach { it.mealPlanId = planId }
        mealPlanDao.insertEntries(entries)
        return planId
    }
    
    suspend fun deleteMealPlan(plan: MealPlan) {
        mealPlanDao.deleteEntriesForPlan(plan.id)
        mealPlanDao.delete(plan)
    }
    
    suspend fun getLatestMealPlan(): MealPlan? = mealPlanDao.getLatest()
    
    suspend fun getMealPlanWithEntries(id: Long): MealPlanWithEntries? = 
        mealPlanDao.getPlanWithEntries(id)
    
    suspend fun generateMealPlan(
        days: Int = 7,
        dishesPerMeal: Int = 3,
        avoidRepeats: Boolean = true
    ): MealPlan {
        val plan = MealPlan(
            days = days,
            avoidRepeats = avoidRepeats
        )
        
        // Get all meals by category
        val breakfastMeals = mealDao.getByCategoryList(Category.BREAKFAST)
        val lunchMeals = mealDao.getByCategoryList(Category.LUNCH)
        val dinnerMeals = mealDao.getByCategoryList(Category.DINNER)
        
        val entries = mutableListOf<MealPlanEntry>()
        var mealIdCounter = 0L
        
        for (day in 0 until days) {
            // Generate breakfast dishes
            val breakfastSelection = selectRandomMeals(
                breakfastMeals,
                dishesPerMeal,
                if (avoidRepeats) entries.map { it.mealId }.toSet() else emptySet()
            )
            
            breakfastSelection.forEachIndexed { index, meal ->
                entries.add(
                    MealPlanEntry(
                        mealPlanId = 0, // Will be set after plan insertion
                        mealId = meal.id,
                        dayIndex = day,
                        mealTime = 0, // breakfast
                        position = index
                    )
                )
                mealIdCounter = meal.id
            }
            
            // Generate lunch dishes
            val lunchSelection = selectRandomMeals(
                lunchMeals,
                dishesPerMeal,
                if (avoidRepeats) entries.map { it.mealId }.toSet() else emptySet()
            )
            
            lunchSelection.forEachIndexed { index, meal ->
                entries.add(
                    MealPlanEntry(
                        mealPlanId = 0,
                        mealId = meal.id,
                        dayIndex = day,
                        mealTime = 1, // lunch
                        position = index
                    )
                )
                mealIdCounter = meal.id
            }
            
            // Generate dinner dishes
            val dinnerSelection = selectRandomMeals(
                dinnerMeals,
                dishesPerMeal,
                if (avoidRepeats) entries.map { it.mealId }.toSet() else emptySet()
            )
            
            dinnerSelection.forEachIndexed { index, meal ->
                entries.add(
                    MealPlanEntry(
                        mealPlanId = 0,
                        mealId = meal.id,
                        dayIndex = day,
                        mealTime = 2, // dinner
                        position = index
                    )
                )
                mealIdCounter = meal.id
            }
        }
        
        val planId = saveMealPlan(plan, entries)
        return plan.copy(id = planId)
    }
    
    private fun selectRandomMeals(
        meals: List<Meal>,
        count: Int,
        excludedIds: Set<Long> = emptySet()
    ): List<Meal> {
        if (meals.isEmpty()) return emptyList()
        
        val availableMeals = meals.filter { it.id !in excludedIds }
        if (availableMeals.isEmpty()) return emptyList()
        
        return if (availableMeals.size <= count) {
            availableMeals
        } else {
            availableMeals.shuffled().take(count)
        }
    }
}