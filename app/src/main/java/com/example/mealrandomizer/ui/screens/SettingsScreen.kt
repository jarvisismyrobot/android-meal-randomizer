package com.example.mealrandomizer.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.viewmodel.SettingsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val avoidRepeats by viewModel.avoidRepeats.collectAsState()
    var backButtonEnabled by remember { mutableStateOf(true) }
    var exportInProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = avoidRepeats,
                onCheckedChange = { viewModel.setAvoidRepeats(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.no_repeat))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // History section
        Text(
            text = "歷史記錄",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { navController.navigate("history") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("查看歷史記錄")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 匯出按鈕
        Button(
            onClick = {
                if (!exportInProgress) {
                    exportInProgress = true
                    coroutineScope.launch {
                        val meals = viewModel.getMealsForExport()
                        val gson = Gson()
                        val mealType = object : TypeToken<List<Meal>>() {}.type
                        val json = gson.toJson(meals, mealType)
                        
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "application/json"
                            putExtra(Intent.EXTRA_TEXT, json)
                            putExtra(Intent.EXTRA_SUBJECT, "餸菜備份")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "分享餸菜備份"))
                        exportInProgress = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !exportInProgress
        ) {
            if (exportInProgress) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Text("匯出餸菜 (JSON)")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
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
}