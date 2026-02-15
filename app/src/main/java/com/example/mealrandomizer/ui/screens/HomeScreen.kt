package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
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
import com.example.mealrandomizer.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showRandomMealDialog by remember { mutableStateOf(false) }
    var randomMeal by remember { mutableStateOf<Meal?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("addEdit") }) {
                Text(stringResource(R.string.add_meal))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.generateRandomMeal { meal ->
                    randomMeal = meal
                    showRandomMealDialog = true
                }
            }) {
                Text(stringResource(R.string.generate_meal))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("history") }) {
                Text(stringResource(R.string.history))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("settings") }) {
                Text(stringResource(R.string.settings))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("search") }) {
                Text(stringResource(R.string.search))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.preloadSampleMeals()
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("已載入30款樣本餸菜")
                }
            }) {
                Text(stringResource(R.string.preload_sample_meals))
            }
        }
    }

    // Dialog to show random meal result
    if (showRandomMealDialog) {
        AlertDialog(
            onDismissRequest = { showRandomMealDialog = false },
            title = { Text(text = stringResource(R.string.meal_generated)) },
            text = {
                if (randomMeal != null) {
                    Column {
                        Text(text = "名稱: ${randomMeal!!.name}")
                        Text(text = "描述: ${randomMeal!!.description}")
                        Text(text = "難度: ${randomMeal!!.difficulty}")
                        Text(text = "烹調時間: ${randomMeal!!.cookingTimeMinutes} 分鐘")
                        randomMeal!!.calories?.let {
                            Text(text = "卡路里: $it kcal")
                        }
                    }
                } else {
                    Text(text = "暫無餸菜，請先添加或載入樣本")
                }
            },
            confirmButton = {
                Button(onClick = { showRandomMealDialog = false }) {
                    Text("確定")
                }
            }
        )
    }
}