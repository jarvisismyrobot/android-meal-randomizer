package com.example.mealrandomizer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mealrandomizer.ui.screens.HomeScreen
import com.example.mealrandomizer.ui.screens.AddEditMealScreen
import com.example.mealrandomizer.ui.screens.HistoryScreen
import com.example.mealrandomizer.ui.screens.SettingsScreen
import com.example.mealrandomizer.ui.screens.SearchScreen

@Composable
fun MealRandomizerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("addEdit") { AddEditMealScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("search") { SearchScreen(navController) }
    }
}