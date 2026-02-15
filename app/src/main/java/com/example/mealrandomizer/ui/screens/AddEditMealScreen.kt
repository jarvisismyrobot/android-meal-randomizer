package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.Difficulty
import com.example.mealrandomizer.viewmodel.AddEditMealViewModel

@Composable
fun AddEditMealScreen(
    navController: NavController,
    viewModel: AddEditMealViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()
    val cookingTime by viewModel.cookingTime.collectAsState()
    val calories by viewModel.calories.collectAsState()

    val difficultyText = when (difficulty) {
        Difficulty.EASY -> stringResource(R.string.easy)
        Difficulty.MEDIUM -> stringResource(R.string.medium)
        Difficulty.HARD -> stringResource(R.string.hard)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_edit_meal_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
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
        // Difficulty dropdown
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = difficultyText,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.difficulty)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Difficulty.values().forEach { diff ->
                    val diffText = when (diff) {
                        Difficulty.EASY -> stringResource(R.string.easy)
                        Difficulty.MEDIUM -> stringResource(R.string.medium)
                        Difficulty.HARD -> stringResource(R.string.hard)
                    }
                    DropdownMenuItem(
                        text = { Text(diffText) },
                        onClick = {
                            viewModel.updateDifficulty(diff)
                            expanded = false
                        }
                    )
                }
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
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    viewModel.saveMeal()
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}