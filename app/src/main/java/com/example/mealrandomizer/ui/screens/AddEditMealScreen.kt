package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.viewmodel.AddEditMealViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEditMealScreen(
    navController: NavController,
    mealId: Long = -1L,
    viewModel: AddEditMealViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val cookingTime by viewModel.cookingTime.collectAsState()
    val calories by viewModel.calories.collectAsState()
    val breakfastSelected by viewModel.breakfastSelected.collectAsState()
    val lunchSelected by viewModel.lunchSelected.collectAsState()
    val dinnerSelected by viewModel.dinnerSelected.collectAsState()
    val saveError by viewModel.saveError.collectAsState()
    var cancelButtonEnabled by remember { mutableStateOf(true) }
    var saveButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(mealId) {
        if (mealId > 0) {
            viewModel.loadMeal(mealId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (mealId > 0) "編輯菜式" else "新增菜式",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Error message display
        saveError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text(stringResource(R.string.meal_name)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text(stringResource(R.string.description)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Meal time selection checkboxes
        Text(
            text = "適用時段",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = breakfastSelected,
                    onCheckedChange = { viewModel.updateBreakfastSelected(it) }
                )
                Text("🌅 早餐", modifier = Modifier.padding(start = 4.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = lunchSelected,
                    onCheckedChange = { viewModel.updateLunchSelected(it) }
                )
                Text("☀️ 午餐", modifier = Modifier.padding(start = 4.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = dinnerSelected,
                    onCheckedChange = { viewModel.updateDinnerSelected(it) }
                )
                Text("🌙 晚餐", modifier = Modifier.padding(start = 4.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = cookingTime,
            onValueChange = { viewModel.updateCookingTime(it) },
            label = { Text(stringResource(R.string.cooking_time)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = calories,
            onValueChange = { viewModel.updateCalories(it) },
            label = { Text(stringResource(R.string.calories)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (cancelButtonEnabled) {
                        cancelButtonEnabled = false
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = cancelButtonEnabled
            ) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    if (saveButtonEnabled) {
                        saveButtonEnabled = false
                        coroutineScope.launch {
                            val success = viewModel.saveMeal()
                            if (success) {
                                navController.popBackStack()
                            } else {
                                // Re-enable button on error
                                saveButtonEnabled = true
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = saveButtonEnabled
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}