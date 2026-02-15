package com.example.mealrandomizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
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
}