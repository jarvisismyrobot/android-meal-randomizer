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
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        // TODO: Settings for days (1-7)
        Text("Days to plan: 7")
        Spacer(modifier = Modifier.height(8.dp))
        Slider(value = 7f, onValueChange = {}, valueRange = 1f..7f, steps = 6)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = true,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.no_repeat))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}