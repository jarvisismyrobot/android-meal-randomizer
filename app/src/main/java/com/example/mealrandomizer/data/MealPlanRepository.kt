@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.mealrandomizer.data

import kotlinx.coroutines.flow.*
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
        val updatedEntries = entries.map { it.copy(mealPlanId = planId) }
        mealPlanDao.insertEntries(updatedEntries)
        return planId
    }
    
    suspend fun deleteMealPlan(plan: MealPlan) {
        mealPlanDao.deleteEntriesForPlan(plan.id)
        mealPlanDao.delete(plan)
    }
    
    suspend fun getLatestMealPlan(): MealPlan? = mealPlanDao.getLatest()
    
    suspend fun getMealPlanWithEntries(id: Long): MealPlanWithEntries? = 
        mealPlanDao.getPlanWithEntries(id)
    
    suspend fun getLatestMealPlanWithEntries(): MealPlanWithEntries? {
        val latestPlan = mealPlanDao.getLatest()
        return latestPlan?.let { mealPlanDao.getPlanWithEntries(it.id) }
    }
    
    suspend fun isMealUsedInPlans(mealId: Long): Boolean {
        val usageCount = mealPlanDao.countMealUsage(mealId)
        return usageCount > 0
    }
    
    fun getLatestMealPlanWithEntriesFlow(): Flow<MealPlanWithEntries?> = 
        mealPlanDao.getAll()
            .map { plans -> plans.firstOrNull() }
            .flatMapLatest { plan ->
                if (plan != null) {
                    // Create a flow that fetches the full plan with entries
                    flow {
                        val planWithEntries = mealPlanDao.getPlanWithEntries(plan.id)
                        emit(planWithEntries)
                    }
                } else {
                    flowOf(null)
                }
            }
    
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
        val allUsedMealIds = mutableSetOf<Long>() // For global avoidRepeats
        
        for (day in 0 until days) {
            val dayUsedMealIds = mutableSetOf<Long>() // For within-day duplicates prevention
            
            // Generate breakfast dishes
            val breakfastExcludedIds = mutableSetOf<Long>()
            if (avoidRepeats) breakfastExcludedIds.addAll(allUsedMealIds)
            breakfastExcludedIds.addAll(dayUsedMealIds)
            
            val breakfastSelection = selectRandomMeals(
                breakfastMeals,
                dishesPerMeal,
                breakfastExcludedIds
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
                allUsedMealIds.add(meal.id)
                dayUsedMealIds.add(meal.id)
            }
            
            // Generate lunch dishes
            val lunchExcludedIds = mutableSetOf<Long>()
            if (avoidRepeats) lunchExcludedIds.addAll(allUsedMealIds)
            lunchExcludedIds.addAll(dayUsedMealIds)
            
            val lunchSelection = selectRandomMeals(
                lunchMeals,
                dishesPerMeal,
                lunchExcludedIds
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
                allUsedMealIds.add(meal.id)
                dayUsedMealIds.add(meal.id)
            }
            
            // Generate dinner dishes
            val dinnerExcludedIds = mutableSetOf<Long>()
            if (avoidRepeats) dinnerExcludedIds.addAll(allUsedMealIds)
            dinnerExcludedIds.addAll(dayUsedMealIds)
            
            val dinnerSelection = selectRandomMeals(
                dinnerMeals,
                dishesPerMeal,
                dinnerExcludedIds
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
                allUsedMealIds.add(meal.id)
                dayUsedMealIds.add(meal.id)
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