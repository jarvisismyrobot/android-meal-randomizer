package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.Category
import com.example.mealrandomizer.viewmodel.HomeViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val meals by viewModel.meals.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredMeals = if (searchQuery.isBlank()) {
        meals
    } else {
        meals.filter { meal ->
            meal.name.contains(searchQuery, ignoreCase = true) ||
            meal.description.contains(searchQuery, ignoreCase = true) ||
            meal.categories.any { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.search),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("搜尋餸菜名稱、描述或類別") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "找到 ${filteredMeals.size} 個結果",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (filteredMeals.isEmpty()) {
            Text(
                text = if (searchQuery.isBlank()) "暫無餸菜，請先添加或載入樣本" else "找不到相關餸菜",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredMeals) { meal ->
                    MealItemCard(meal = meal)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("返回")
        }
    }
}

@Composable
fun MealItemCard(meal: Meal) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = meal.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = meal.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "難度: ${meal.difficulty}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "時間: ${meal.cookingTimeMinutes}分鐘",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                meal.calories?.let {
                    Text(
                        text = "卡路里: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}