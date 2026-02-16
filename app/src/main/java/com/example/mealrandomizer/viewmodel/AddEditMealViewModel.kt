package com.example.mealrandomizer.viewmodel

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Category
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
    private val _cookingTime = MutableStateFlow("")
    val cookingTime: StateFlow<String> = _cookingTime
    private val _calories = MutableStateFlow("")
    val calories: StateFlow<String> = _calories
    
    // Meal time selection (breakfast, lunch, dinner)
    private val _breakfastSelected = MutableStateFlow(true)
    val breakfastSelected: StateFlow<Boolean> = _breakfastSelected
    private val _lunchSelected = MutableStateFlow(true)
    val lunchSelected: StateFlow<Boolean> = _lunchSelected
    private val _dinnerSelected = MutableStateFlow(true)
    val dinnerSelected: StateFlow<Boolean> = _dinnerSelected
    
    private val _saveError = MutableStateFlow<String?>(null)
    val saveError: StateFlow<String?> = _saveError
    
    private var currentMealId: Long = -1L

    fun loadMeal(mealId: Long) {
        if (mealId <= 0) return
        currentMealId = mealId
        viewModelScope.launch {
            val meal = repository.getMeal(mealId)
            meal?.let {
                _name.value = it.name
                _description.value = it.description
                _cookingTime.value = it.cookingTimeMinutes.toString()
                _calories.value = it.calories?.toString() ?: ""
                
                // Set meal time checkboxes based on categories
                _breakfastSelected.value = it.categories.contains(Category.BREAKFAST)
                _lunchSelected.value = it.categories.contains(Category.LUNCH)
                _dinnerSelected.value = it.categories.contains(Category.DINNER)
            }
        }
    }

    fun updateName(value: String) { _name.value = value }
    fun updateDescription(value: String) { _description.value = value }
    fun updateCookingTime(value: String) { _cookingTime.value = value }
    fun updateCalories(value: String) { _calories.value = value }
    fun updateBreakfastSelected(selected: Boolean) { _breakfastSelected.value = selected }
    fun updateLunchSelected(selected: Boolean) { _lunchSelected.value = selected }
    fun updateDinnerSelected(selected: Boolean) { _dinnerSelected.value = selected }

    suspend fun saveMeal(): Boolean {
        // Validate name is not empty or blank
        val trimmedName = _name.value.trim()
        if (trimmedName.isBlank()) {
            _saveError.value = "菜式名稱不能為空"
            return false
        }
        
        // Check for duplicate name (case-insensitive, trim)
        val existingCount = repository.countMealsByName(trimmedName, excludeId = currentMealId)
        if (existingCount > 0) {
            _saveError.value = "菜式名稱已存在，請使用其他名稱"
            return false
        }
        
        _saveError.value = null
        
        val cookingTimeInt = _cookingTime.value.toIntOrNull() ?: 0
        val caloriesInt = _calories.value.toIntOrNull()
        
        // Build categories list based on selected meal times
        val categories = mutableListOf<Category>()
        if (_breakfastSelected.value) categories.add(Category.BREAKFAST)
        if (_lunchSelected.value) categories.add(Category.LUNCH)
        if (_dinnerSelected.value) categories.add(Category.DINNER)
        
        // If no meal time selected, default to all three
        if (categories.isEmpty()) {
            categories.addAll(listOf(Category.BREAKFAST, Category.LUNCH, Category.DINNER))
        }
        
        val meal = Meal(
            id = if (currentMealId > 0) currentMealId else 0,
            name = trimmedName,
            description = _description.value,
            cookingTimeMinutes = cookingTimeInt,
            calories = caloriesInt,
            categories = categories
        )
        
        try {
            if (currentMealId > 0) {
                repository.updateMeal(meal)
            } else {
                val insertedId = repository.insertMeal(meal)
                if (insertedId == -1L) {
                    _saveError.value = "菜式名稱已存在，請使用其他名稱"
                    return false
                }
            }
        } catch (e: android.database.sqlite.SQLiteConstraintException) {
            _saveError.value = "菜式名稱已存在，請使用其他名稱"
            return false
        }
        return true
    }
}