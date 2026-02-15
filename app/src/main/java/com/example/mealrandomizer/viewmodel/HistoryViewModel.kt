package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.MealPlan
import com.example.mealrandomizer.data.MealPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {
    val mealPlans = mealPlanRepository.getAllMealPlans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    suspend fun clearHistory() {
        mealPlanRepository.getAllMealPlans().collect { plans ->
            plans.forEach { mealPlanRepository.deleteMealPlan(it) }
        }
    }
}