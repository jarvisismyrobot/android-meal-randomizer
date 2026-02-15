package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealrandomizer.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Button(onClick = { /* Generate random meal */ }) {
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
    }
}