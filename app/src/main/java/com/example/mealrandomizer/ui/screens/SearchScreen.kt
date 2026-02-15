package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.clickable
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
import com.example.mealrandomizer.viewmodel.SearchViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import com.example.mealrandomizer.data.Category
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
    val filterBreakfast by viewModel.filterBreakfast.collectAsState()
    val filterLunch by viewModel.filterLunch.collectAsState()
    val filterDinner by viewModel.filterDinner.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var mealToDelete by remember { mutableStateOf<Meal?>(null) }
    var backButtonEnabled by remember { mutableStateOf(true) }
    var showDeleteError by remember { mutableStateOf(false) }

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
            onValueChange = { viewModel.setSearchQuery(it) },
            label = { Text("搜尋餸菜名稱、描述或類別") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        // Meal time filter
        Text(
            text = "適用時段篩選:",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = filterBreakfast,
                    onCheckedChange = { viewModel.setFilterBreakfast(it) }
                )
                Text("早餐")
            }
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = filterLunch,
                    onCheckedChange = { viewModel.setFilterLunch(it) }
                )
                Text("午餐")
            }
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = filterDinner,
                    onCheckedChange = { viewModel.setFilterDinner(it) }
                )
                Text("晚餐")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "找到 ${searchResults.size} 個結果",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (searchResults.isEmpty()) {
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
                items(searchResults) { meal ->
                    MealItemCard(
                        meal = meal,
                        onEdit = {
                            navController.navigate("addEdit/${meal.id}")
                        },
                        onDelete = {
                            mealToDelete = meal
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (backButtonEnabled) {
                    backButtonEnabled = false
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = backButtonEnabled
        ) {
            Text("返回")
        }
    }

    // Delete confirmation dialog
    if (mealToDelete != null) {
        AlertDialog(
            onDismissRequest = { mealToDelete = null },
            title = { Text("刪除餸菜") },
            text = { Text("確定要刪除「${mealToDelete!!.name}」嗎？") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val success = viewModel.deleteMeal(mealToDelete!!)
                            if (success) {
                                mealToDelete = null
                            } else {
                                mealToDelete = null
                                showDeleteError = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("刪除")
                }
            },
            dismissButton = {
                Button(onClick = { mealToDelete = null }) {
                    Text("取消")
                }
            }
        )
    }
    
    // Delete error dialog
    if (showDeleteError) {
        AlertDialog(
            onDismissRequest = { showDeleteError = false },
            title = { Text("無法刪除") },
            text = { Text("此餸菜已出現在餐單中，無法刪除") },
            confirmButton = {
                Button(onClick = { showDeleteError = false }) {
                    Text("確定")
                }
            }
        )
    }
}

@Composable
fun MealItemCard(
    meal: Meal,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "編輯",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "刪除",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = meal.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
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
            // Show meal times
            Spacer(modifier = Modifier.height(4.dp))
            val mealTimes = mutableListOf<String>()
            if (meal.categories.contains(Category.BREAKFAST)) mealTimes.add("早餐")
            if (meal.categories.contains(Category.LUNCH)) mealTimes.add("午餐")
            if (meal.categories.contains(Category.DINNER)) mealTimes.add("晚餐")
            if (mealTimes.isNotEmpty()) {
                Text(
                    text = "適用時段: ${mealTimes.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}