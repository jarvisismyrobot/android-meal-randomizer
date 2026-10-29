package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.data.MealPlanWithEntries
import com.example.mealrandomizer.ui.theme.Espresso
import com.example.mealrandomizer.ui.theme.EspressoSoft
import com.example.mealrandomizer.ui.theme.MealTint
import com.example.mealrandomizer.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val planWithEntries by viewModel.currentMealPlanWithEntries.collectAsState()
    var selectedDay by remember { mutableIntStateOf(0) }

    // Auto-generate a plan on first launch
    LaunchedEffect(Unit) {
        if (planWithEntries == null) {
            viewModel.generateMealPlanAsync { }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { HomeBottomBar(navController, currentRoute = "home") }
    ) { padding ->
        val data = planWithEntries
        if (data == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val days = data.mealPlan.days.coerceAtLeast(1)
        if (selectedDay >= days) selectedDay = 0
        val mealsByDay = remember(data) { groupMealsByDay(data) }
        val dayMeals = mealsByDay[selectedDay] ?: emptyMap()
        val dinnerMeals = dayMeals[2].orEmpty()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { GreetingHeader() }
            item {
                DinnerHero(
                    title = if (selectedDay == 0) "今晚精選" else "第 ${selectedDay + 1} 日晚餐",
                    meals = dinnerMeals,
                    onShuffle = { viewModel.shuffleMeal(selectedDay, 2) }
                )
            }
            item {
                WeekStrip(
                    days = days,
                    selectedDay = selectedDay,
                    onSelect = { selectedDay = it }
                )
            }
            item {
                Text(
                    text = if (selectedDay == 0) "今日餐單" else "第 ${selectedDay + 1} 日餐單",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 18.dp, bottom = 10.dp)
                )
            }
            items(3) { mealTime ->
                MealRow(
                    mealTime = mealTime,
                    meals = dayMeals[mealTime].orEmpty(),
                    onShuffle = { viewModel.shuffleMeal(selectedDay, mealTime) },
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 5.dp)
                )
            }
        }
    }
}

/** Groups entries into dayIndex -> mealTime -> meals (ordered by position). */
private fun groupMealsByDay(data: MealPlanWithEntries): Map<Int, Map<Int, List<Meal>>> {
    val result = mutableMapOf<Int, MutableMap<Int, MutableList<Meal>>>()
    data.entries
        .sortedWith(compareBy({ it.entry.dayIndex }, { it.entry.mealTime }, { it.entry.position }))
        .forEach { e ->
            result.getOrPut(e.entry.dayIndex) { mutableMapOf() }
                .getOrPut(e.entry.mealTime) { mutableListOf() }
                .add(e.meal)
        }
    return result
}

@Composable
private fun GreetingHeader() {
    val hour = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val greeting = when {
        hour < 12 -> "早晨"
        hour < 18 -> "午安"
        else -> "晚上好"
    }
    val dateStr = remember {
        SimpleDateFormat("M月d日 E", Locale.TRADITIONAL_CHINESE).format(Date())
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .padding(top = 10.dp, bottom = 2.dp)
    ) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "$dateStr · 今晚有著落",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}

@Composable
private fun DinnerHero(
    title: String,
    meals: List<Meal>,
    onShuffle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Espresso)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = EspressoSoft,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "三餸",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFC9B8A4)
                )
            }
            Spacer(Modifier.height(10.dp))
            if (meals.isEmpty()) {
                Text(
                    text = "未有餐單",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFFF7EE)
                )
            } else {
                meals.forEachIndexed { index, meal ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8590C).copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = EspressoSoft
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = meal.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFF7EE),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val totalMinutes = meals.sumOf { it.cookingTimeMinutes }
                Text(
                    text = if (meals.isEmpty()) "" else "家常菜 · 約${totalMinutes}分鐘",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFC9B8A4)
                )
                Button(
                    onClick = onShuffle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8590C),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(999.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shuffle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(7.dp))
                    Text("成餐換", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun WeekStrip(
    days: Int,
    selectedDay: Int,
    onSelect: (Int) -> Unit
) {
    val labels = listOf("一", "二", "三", "四", "五", "六", "日")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until days) {
            val selected = i == selectedDay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (selected) MaterialTheme.colorScheme.inverseSurface
                        else Color.Transparent
                    )
                    .clickable { onSelect(i) }
                    .padding(vertical = 9.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels.getOrElse(i) { "${i + 1}" },
                    fontSize = 13.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    color = if (selected) MaterialTheme.colorScheme.inverseOnSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class MealTimeStyle(
    val label: String,
    val icon: ImageVector,
    val tint: Color,
    val tintBg: Color
)

private fun mealTimeStyle(mealTime: Int): MealTimeStyle = when (mealTime) {
    0 -> MealTimeStyle("早餐", Icons.Filled.WbSunny, MealTint.breakfast, MealTint.breakfastBg)
    1 -> MealTimeStyle("午餐", Icons.Filled.Restaurant, MealTint.lunch, MealTint.lunchBg)
    else -> MealTimeStyle("晚餐", Icons.Filled.Nightlight, MealTint.dinner, MealTint.dinnerBg)
}

@Composable
private fun MealRow(
    mealTime: Int,
    meals: List<Meal>,
    onShuffle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val style = mealTimeStyle(mealTime)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(style.tintBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = style.icon,
                    contentDescription = null,
                    tint = style.tint,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(Modifier.width(13.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = style.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (meals.isEmpty()) "未有餐單"
                    else meals.joinToString(" · ") { it.name },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = onShuffle,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Filled.Shuffle,
                    contentDescription = "換一餐",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(17.dp)
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar(navController: NavController, currentRoute: String) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        bottomBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") { launchSingleTop = true } },
            icon = Icons.Filled.Home,
            label = "主頁"
        )
        bottomBarItem(
            selected = false,
            onClick = {
                navController.navigate("search") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = Icons.Filled.Search,
            label = "搵食"
        )
        bottomBarItem(
            selected = false,
            onClick = {
                navController.navigate("addEdit/-1") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = Icons.Filled.Add,
            label = "加菜"
        )
        bottomBarItem(
            selected = false,
            onClick = {
                navController.navigate("history") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = Icons.Filled.History,
            label = "記錄"
        )
        bottomBarItem(
            selected = false,
            onClick = {
                navController.navigate("settings") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = Icons.Filled.Settings,
            label = "設定"
        )
    }
}

@Composable
private fun RowScope.bottomBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
