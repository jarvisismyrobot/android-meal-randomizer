package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.*
import com.example.mealrandomizer.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentMealPlanWithEntries by viewModel.currentMealPlanWithEntries.collectAsState()
    
    // Auto-generate meal plan on first launch if none exists
    LaunchedEffect(Unit) {
        if (currentMealPlanWithEntries == null) {
            viewModel.generateMealPlanAsync { plan ->
                // Plan generated, state will update via flow
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { navController.navigate("addEdit/-1") }) {
                        Icon(Icons.Filled.Add, contentDescription = "加餸")
                    }
                    IconButton(onClick = {
                        viewModel.generateMealPlanAsync { plan ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("已生成7日餐單")
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Casino, contentDescription = "隨機選擇餸菜")
                    }
                    IconButton(onClick = { navController.navigate("history") }) {
                        Icon(Icons.Filled.History, contentDescription = "歷史記錄")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "設定")
                    }
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Filled.Search, contentDescription = "搜尋")
                    }
                    IconButton(onClick = {
                        viewModel.preloadSampleMeals()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("已載入30款樣本餸菜")
                        }
                    }) {
                        Icon(Icons.Filled.Download, contentDescription = "載入樣本餸菜")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Meal plan display
            if (currentMealPlanWithEntries == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                MealPlanDisplay(
                    mealPlanWithEntries = currentMealPlanWithEntries!!,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun MealPlanDisplay(
    mealPlanWithEntries: MealPlanWithEntries,
    modifier: Modifier = Modifier
) {
    val plan = mealPlanWithEntries.mealPlan
    val entries = mealPlanWithEntries.entries
    
    // Group entries by day and meal time
    val daysMap = mutableMapOf<Int, MutableMap<Int, List<MealPlanEntryWithMeal>>>()
    
    entries.forEach { entryWithMeal ->
        val day = entryWithMeal.entry.dayIndex
        val mealTime = entryWithMeal.entry.mealTime
        
        val dayMap = daysMap.getOrPut(day) { mutableMapOf() }
        val mealList = dayMap.getOrPut(mealTime) { mutableListOf() }
        (mealList as MutableList).add(entryWithMeal)
    }
    
    // Sort meals by position
    daysMap.values.forEach { dayMap ->
        dayMap.values.forEach { mealList ->
            (mealList as MutableList).sortBy { it.entry.position }
        }
    }
    
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${plan.days}日餐單",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "已為你生成未來${plan.days}日的餐單",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "避免重複: ${if (plan.avoidRepeats) "是" else "否"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        
        // Display each day
        items(plan.days) { dayIndex ->
            val dayMap = daysMap[dayIndex] ?: emptyMap()
            val breakfastMeals = (dayMap[0] ?: emptyList()).map { it.meal }
            val lunchMeals = (dayMap[1] ?: emptyList()).map { it.meal }
            val dinnerMeals = (dayMap[2] ?: emptyList()).map { it.meal }
            
            DayCard(
                dayIndex = dayIndex + 1,
                breakfastMeals = breakfastMeals,
                lunchMeals = lunchMeals,
                dinnerMeals = dinnerMeals
            )
        }
    }
}

@Composable
fun DayCard(
    dayIndex: Int,
    breakfastMeals: List<Meal>,
    lunchMeals: List<Meal>,
    dinnerMeals: List<Meal>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "第 $dayIndex 日",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // Breakfast section
            Text(
                text = "早餐",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (breakfastMeals.isEmpty()) {
                Text("隨機選擇3款早餐菜式", style = MaterialTheme.typography.titleMedium)
            } else {
                breakfastMeals.forEachIndexed { index, meal ->
                    Text("${index + 1}. ${meal.name}", style = MaterialTheme.typography.titleMedium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Lunch section
            Text(
                text = "午餐",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (lunchMeals.isEmpty()) {
                Text("隨機選擇3款午餐菜式", style = MaterialTheme.typography.titleMedium)
            } else {
                lunchMeals.forEachIndexed { index, meal ->
                    Text("${index + 1}. ${meal.name}", style = MaterialTheme.typography.titleMedium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Dinner section
            Text(
                text = "晚餐",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (dinnerMeals.isEmpty()) {
                Text("隨機選擇3款晚餐菜式", style = MaterialTheme.typography.titleMedium)
            } else {
                dinnerMeals.forEachIndexed { index, meal ->
                    Text("${index + 1}. ${meal.name}", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}