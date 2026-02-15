@file:OptIn(kotlinx.coroutines.FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrandomizer.data.Category
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _filterBreakfast = MutableStateFlow(true)
    val filterBreakfast: StateFlow<Boolean> = _filterBreakfast.asStateFlow()
    
    private val _filterLunch = MutableStateFlow(true)
    val filterLunch: StateFlow<Boolean> = _filterLunch.asStateFlow()
    
    private val _filterDinner = MutableStateFlow(true)
    val filterDinner: StateFlow<Boolean> = _filterDinner.asStateFlow()
    
    val searchResults = combine(
        _searchQuery.debounce(300).distinctUntilChanged(),
        _filterBreakfast,
        _filterLunch,
        _filterDinner
    ) { query, breakfast, lunch, dinner ->
        query to listOf(
            if (breakfast) Category.BREAKFAST else null,
            if (lunch) Category.LUNCH else null,
            if (dinner) Category.DINNER else null
        ).filterNotNull()
    }.flatMapLatest { (query, selectedCategories) ->
        if (query.isBlank() && selectedCategories.isEmpty()) {
            flowOf(emptyList())
        } else if (query.isBlank()) {
            // Filter by categories only
            if (selectedCategories.isEmpty()) {
                repository.getAllMeals()
            } else {
                val flows = selectedCategories.map { category ->
                    repository.getMealsByCategory(category)
                }
                combine(flows) { arrays ->
                    arrays.flatMap { it }.distinctBy { it.id }
                }
            }
        } else {
            // Search with query, then filter by categories
            repository.searchMeals(query).map { meals ->
                if (selectedCategories.isEmpty()) {
                    meals
                } else {
                    meals.filter { meal ->
                        meal.categories.any { it in selectedCategories }
                    }.distinctBy { it.id }
                }
            }
        }
    }
    
    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }
    
    fun setFilterBreakfast(enabled: Boolean) {
        viewModelScope.launch {
            _filterBreakfast.value = enabled
        }
    }
    
    fun setFilterLunch(enabled: Boolean) {
        viewModelScope.launch {
            _filterLunch.value = enabled
        }
    }
    
    fun setFilterDinner(enabled: Boolean) {
        viewModelScope.launch {
            _filterDinner.value = enabled
        }
    }
    
    suspend fun deleteMeal(meal: Meal) {
        repository.deleteMeal(meal)
    }
}