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
    private val _difficulty = MutableStateFlow(Difficulty.MEDIUM)
    val difficulty: StateFlow<Difficulty> = _difficulty
    private val _cookingTime = MutableStateFlow("")
    val cookingTime: StateFlow<String> = _cookingTime
    private val _calories = MutableStateFlow("")
    val calories: StateFlow<String> = _calories

    fun updateName(value: String) { _name.value = value }
    fun updateDescription(value: String) { _description.value = value }
    fun updateDifficulty(value: Difficulty) { _difficulty.value = value }
    fun updateCookingTime(value: String) { _cookingTime.value = value }
    fun updateCalories(value: String) { _calories.value = value }

    fun saveMeal() {
        viewModelScope.launch {
            val cookingTimeInt = _cookingTime.value.toIntOrNull() ?: 0
            val caloriesInt = _calories.value.toIntOrNull()
            val meal = Meal(
                name = _name.value,
                description = _description.value,
                difficulty = _difficulty.value,
                cookingTimeMinutes = cookingTimeInt,
                calories = caloriesInt,
                categories = emptyList() // TODO: Add category selection
            )
            repository.insertMeal(meal)
        }
    }
}