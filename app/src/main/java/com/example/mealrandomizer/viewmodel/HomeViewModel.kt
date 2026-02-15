package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealPlan
import com.example.mealrandomizer.data.MealPlanRepository
import com.example.mealrandomizer.data.MealPlanWithEntries
import com.example.mealrandomizer.data.MealRepository
import com.example.mealrandomizer.data.sampleMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealRepository,
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {
    val meals = repository.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val currentMealPlan = mealPlanRepository.getAllMealPlans()
        .map { plans -> plans.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    
    val currentMealPlanWithEntries = mealPlanRepository.getLatestMealPlanWithEntriesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun generateRandomMeal(onResult: (Meal?) -> Unit) {
        viewModelScope.launch {
            val meal = repository.getRandomMeal()
            onResult(meal)
        }
    }
    
    suspend fun generateMealPlan(days: Int = 7, avoidRepeats: Boolean = true): MealPlan {
        return mealPlanRepository.generateMealPlan(days = days, avoidRepeats = avoidRepeats)
    }
    
    fun generateMealPlanAsync(days: Int = 7, avoidRepeats: Boolean = true, onResult: (MealPlan?) -> Unit) {
        viewModelScope.launch {
            val plan = generateMealPlan(days, avoidRepeats)
            onResult(plan)
        }
    }
    
    suspend fun loadLatestMealPlan(): MealPlan? {
        return mealPlanRepository.getLatestMealPlan()
    }

    fun preloadSampleMeals() {
        viewModelScope.launch {
            // Check if meals already exist to avoid duplicates
            val existingMeals = repository.getAllMeals().first()
            
            if (existingMeals.isEmpty()) {
                sampleMeals.forEach { repository.insertMeal(it) }
            }
        }
    }
}