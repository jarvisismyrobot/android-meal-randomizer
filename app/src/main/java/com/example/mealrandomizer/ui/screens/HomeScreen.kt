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
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealPlan
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
    val currentMealPlan by viewModel.currentMealPlan.collectAsState()
    
    // Auto-generate meal plan on first launch if none exists
    LaunchedEffect(Unit) {
        if (currentMealPlan == null) {
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
                    IconButton(onClick = { navController.navigate("addEdit") }) {
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
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            // Meal plan display
            if (currentMealPlan == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                MealPlanDisplay(
                    mealPlan = currentMealPlan!!,
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
    mealPlan: MealPlan,
    modifier: Modifier = Modifier
) {
    // For now, show a placeholder since we don't have the full meal plan with entries
    // In a real implementation, you would fetch MealPlanWithEntries
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
                        text = "7日餐單",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "已為你生成未來${mealPlan.days}日的餐單",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "避免重複: ${if (mealPlan.avoidRepeats) "是" else "否"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        
        // Placeholder for days
        items(mealPlan.days) { dayIndex ->
            DayCard(dayIndex = dayIndex + 1)
        }
    }
}

@Composable
fun DayCard(dayIndex: Int) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "第 $dayIndex 日",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Breakfast
            Text(
                text = "早餐",
                style = MaterialTheme.typography.titleMedium
            )
            Text("隨機選擇3款早餐菜式")
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Lunch
            Text(
                text = "午餐", 
                style = MaterialTheme.typography.titleMedium
            )
            Text("隨機選擇3款午餐菜式")
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Dinner
            Text(
                text = "晚餐",
                style = MaterialTheme.typography.titleMedium
            )
            Text("隨機選擇3款晚餐菜式")
        }
    }
}