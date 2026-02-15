package com.example.mealrandomizer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val daysToPlan by viewModel.daysToPlan.collectAsState()
    val avoidRepeats by viewModel.avoidRepeats.collectAsState()
    
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
        
        Text("Days to plan: $daysToPlan")
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = daysToPlan.toFloat(),
            onValueChange = { newValue ->
                viewModel.setDaysToPlan(newValue.roundToInt())
            },
            valueRange = 1f..7f,
            steps = 6
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = avoidRepeats,
                onCheckedChange = { viewModel.setAvoidRepeats(it) }
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