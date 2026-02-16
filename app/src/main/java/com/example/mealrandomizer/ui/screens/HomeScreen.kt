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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.*
import com.example.mealrandomizer.ui.theme.FoodColors
import com.example.mealrandomizer.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
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
            viewModel.generateMealPlanAsync { _ ->
                // Plan generated, state will update via flow
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(
                tonalElevation = 16.dp,
                shadowElevation = 8.dp,
                shape = MaterialTheme.shapes.large.copy(
                    topStart = MaterialTheme.shapes.large.topStart,
                    topEnd = MaterialTheme.shapes.large.topEnd,
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("addEdit/-1") },
                        icon = {
                            Icon(Icons.Filled.Add, contentDescription = "加菜")
                        },
                        label = { Text("加菜", style = MaterialTheme.typography.labelSmall) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            viewModel.generateMealPlanAsync { _ ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("已生成7日餐單")
                                }
                            }
                        },
                        icon = {
                            Icon(Icons.Filled.Refresh, contentDescription = "重新生成餐單")
                        },
                        label = { Text("刷新", style = MaterialTheme.typography.labelSmall) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("settings") },
                        icon = {
                            Icon(Icons.Filled.Settings, contentDescription = "設定")
                        },
                        label = { Text("設定", style = MaterialTheme.typography.labelSmall) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("search") },
                        icon = {
                            Icon(Icons.Filled.Search, contentDescription = "搜尋")
                        },
                        label = { Text("搜尋", style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // App header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = "🍽️ ${stringResource(R.string.app_name)} 🍜",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            
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
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.RestaurantMenu,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${plan.days}日餐單",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "已為您生成未來${plan.days}日餐單",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
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
    var expanded by remember { mutableStateOf(true) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Day header with toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            topStart = MaterialTheme.shapes.extraLarge.topStart,
                            topEnd = MaterialTheme.shapes.extraLarge.topEnd,
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "第 $dayIndex 日",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "收起" else "展開",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            if (expanded) {
                // Meals grid
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Breakfast
                    MealTimeCard(
                        title = "🌅 早餐",
                        meals = breakfastMeals,
                        tintColor = FoodColors.breakfast,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Lunch
                    MealTimeCard(
                        title = "☀️ 午餐",
                        meals = lunchMeals,
                        tintColor = FoodColors.lunch,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Dinner
                    MealTimeCard(
                        title = "🌙 晚餐",
                        meals = dinnerMeals,
                        tintColor = FoodColors.dinner,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun MealTimeCard(
    title: String,
    meals: List<Meal>,
    tintColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = tintColor.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                thickness = 1.dp,
                color = tintColor.copy(alpha = 0.3f)
            )
            
            if (meals.isEmpty()) {
                Text(
                    text = "選擇3款",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                meals.forEachIndexed { index, meal ->
                    Text(
                        text = "${index + 1}. ${meal.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}