package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.FlowRow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.*
import com.example.mealrandomizer.ui.theme.FoodColors
import com.example.mealrandomizer.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeleteError by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "🔍 ${stringResource(R.string.search)}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("搜尋餸菜名稱或描述...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.clearSearch() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "清除"
                            )
                        }
                    }
                }
            }
            
            // Meal time filter
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterChip(
                        selected = filterBreakfast,
                        onClick = { viewModel.setFilterBreakfast(!filterBreakfast) },
                        label = { Text("🌅 早餐") },
                        leadingIcon = if (filterBreakfast) {
                            { Icon(Icons.Filled.Check, null, modifier = Modifier.size(16.dp)) }
                        } else null
                    )
                    FilterChip(
                        selected = filterLunch,
                        onClick = { viewModel.setFilterLunch(!filterLunch) },
                        label = { Text("☀️ 午餐") },
                        leadingIcon = if (filterLunch) {
                            { Icon(Icons.Filled.Check, null, modifier = Modifier.size(16.dp)) }
                        } else null
                    )
                    FilterChip(
                        selected = filterDinner,
                        onClick = { viewModel.setFilterDinner(!filterDinner) },
                        label = { Text("🌙 晚餐") },
                        leadingIcon = if (filterDinner) {
                            { Icon(Icons.Filled.Check, null, modifier = Modifier.size(16.dp)) }
                        } else null
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search results
            when {
                searchResults.isEmpty() && searchQuery.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "輸入關鍵字搜尋餸菜",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                searchResults.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "找不到相關餸菜",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "嘗試其他關鍵字或調整篩選條件",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(searchResults) { meal ->
                            MealCard(
                                meal = meal,
                                onClick = { navController.navigate("addEdit/${meal.id}") },
                                onDelete = {
                                    mealToDelete = meal
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog && mealToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                mealToDelete = null
            },
            title = { Text("刪除餸菜") },
            text = { Text("確定要刪除「${mealToDelete?.name}」嗎？此操作無法還原。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            mealToDelete?.let { meal ->
                                val canDelete = viewModel.canDeleteMeal(meal.id)
                                if (canDelete) {
                                    viewModel.deleteMeal(meal)
                                    mealToDelete = null
                                } else {
                                    showDeleteError = true
                                }
                                showDeleteDialog = false
                            }
                        }
                    }
                ) {
                    Text("刪除")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        mealToDelete = null
                    }
                ) {
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
            text = { Text("此餸菜已在餐單中使用，請先從餐單中移除。") },
            confirmButton = {
                TextButton(
                    onClick = { showDeleteError = false }
                ) {
                    Text("確定")
                }
            }
        )
    }
}

@Composable
fun MealCard(
    meal: Meal,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isHovered) 8.dp else 2.dp
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Meal name and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    IconButton(
                        onClick = onClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "編輯",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "刪除",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            
            // Meal description
            if (meal.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = meal.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Categories
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (meal.categories.contains(Category.BREAKFAST)) {
                    CategoryChip(text = "早餐", color = FoodColors.breakfast)
                }
                if (meal.categories.contains(Category.LUNCH)) {
                    CategoryChip(text = "午餐", color = FoodColors.lunch)
                }
                if (meal.categories.contains(Category.DINNER)) {
                    CategoryChip(text = "晚餐", color = FoodColors.dinner)
                }
                if (meal.categories.contains(Category.VEGETARIAN)) {
                    CategoryChip(text = "素食", color = FoodColors.vegetarian)
                }
                if (meal.categories.contains(Category.QUICK)) {
                    CategoryChip(text = "快速", color = FoodColors.quickMeal)
                }
            }
            
            // Cooking time and calories
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.cookingTimeMinutes}分鐘",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (meal.calories != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${meal.calories}卡",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
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