package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Difficulty
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMealViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description
    private val _difficulty = MutableStateFlow("MEDIUM")
    val difficulty: StateFlow<String> = _difficulty
    private val _cookingTime = MutableStateFlow("")
    val cookingTime: StateFlow<String> = _cookingTime
    private val _calories = MutableStateFlow("")
    val calories: StateFlow<String> = _calories

    fun updateName(value: String) { _name.value = value }
    fun updateDescription(value: String) { _description.value = value }
    fun updateDifficulty(value: String) { _difficulty.value = value }
    fun updateCookingTime(value: String) { _cookingTime.value = value }
    fun updateCalories(value: String) { _calories.value = value }

    fun saveMeal() {
        viewModelScope.launch {
            val cookingTimeInt = _cookingTime.value.toIntOrNull() ?: 0
            val caloriesInt = _calories.value.toIntOrNull()
            val difficultyEnum = try {
                Difficulty.valueOf(_difficulty.value.uppercase())
            } catch (e: IllegalArgumentException) {
                Difficulty.MEDIUM
            }
            val meal = Meal(
                name = _name.value,
                description = _description.value,
                difficulty = difficultyEnum,
                cookingTimeMinutes = cookingTimeInt,
                calories = caloriesInt,
                categories = emptyList() // TODO: Add category selection
            )
            repository.insertMeal(meal)
        }
    }
}