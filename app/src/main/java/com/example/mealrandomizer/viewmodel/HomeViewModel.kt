package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealRepository
import com.example.mealrandomizer.data.sampleMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    val meals = repository.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun generateRandomMeal(onResult: (Meal?) -> Unit) {
        viewModelScope.launch {
            val meal = repository.getRandomMeal()
            onResult(meal)
        }
    }

    fun preloadSampleMeals() {
        viewModelScope.launch {
            sampleMeals.forEach { repository.insertMeal(it) }
        }
    }
}