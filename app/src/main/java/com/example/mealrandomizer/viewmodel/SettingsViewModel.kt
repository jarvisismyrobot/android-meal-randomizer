package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _daysToPlan = MutableStateFlow(7)
    val daysToPlan: StateFlow<Int> = _daysToPlan.asStateFlow()
    
    private val _avoidRepeats = MutableStateFlow(false)
    val avoidRepeats: StateFlow<Boolean> = _avoidRepeats.asStateFlow()
    
    fun setDaysToPlan(days: Int) {
        viewModelScope.launch {
            _daysToPlan.value = days.coerceIn(1, 7)
        }
    }
    
    fun setAvoidRepeats(avoid: Boolean) {
        viewModelScope.launch {
            _avoidRepeats.value = avoid
        }
    }
    
    suspend fun getMealsForExport(): List<Meal> = repository.getAllMealsList()
    
    suspend fun importMealsFromJson(json: String): Boolean {
        return try {
            val gson = Gson()
            val mealType = object : TypeToken<List<Meal>>() {}.type
            val meals = gson.fromJson<List<Meal>>(json, mealType)
            if (meals != null) {
                repository.importMeals(meals)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}