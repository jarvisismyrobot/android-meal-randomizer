package com.example.mealrandomizer.ui.screens
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.*
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealrandomizer.R
import com.example.mealrandomizer.data.Meal
import com.example.mealrandomizer.viewmodel.SettingsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import androidx.core.content.FileProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val avoidRepeats by viewModel.avoidRepeats.collectAsState()
    var exportInProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "⚙️ ${stringResource(R.string.settings)}",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Meal settings section
            item {
                SettingsCategory(title = "餐單設定")
            }
            
            item {
                SettingsItem(
                    icon = Icons.Filled.Repeat,
                    title = "避免重複",
                    subtitle = "防止同一菜式在餐單中重複出現",
                    trailing = {
                        Switch(
                            checked = avoidRepeats,
                            onCheckedChange = { viewModel.setAvoidRepeats(it) },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                )
            }
            
            item {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            
            // Data management section
            item {
                SettingsCategory(title = "數據管理")
            }
            
            item {
                SettingsItem(
                    icon = Icons.Filled.History,
                    title = "歷史記錄",
                    subtitle = "查看過往生成的餐單",
                    onClick = { navController.navigate("history") }
                )
            }
            
            item {
                SettingsItem(
                    icon = Icons.Filled.Download,
                    title = "匯出菜式",
                    subtitle = "備份所有菜式資料 (JSON格式)",
                    onClick = {
                        if (!exportInProgress) {
                            exportInProgress = true
                            coroutineScope.launch {
                                val meals = viewModel.getMealsForExport()
                                val gson = Gson()
                                val mealType = object : TypeToken<List<Meal>>() {}.type
                                val json = gson.toJson(meals, mealType)
                                
                                try {
                                    // Create a file in app's cache directory
                                    val fileName = "菜式備份_${System.currentTimeMillis()}.json"
                                    val cacheDir = context.cacheDir
                                    val exportFile = File(cacheDir, fileName)
                                    
                                    // Write JSON to file
                                    FileWriter(exportFile).use { writer ->
                                        writer.write(json)
                                    }
                                    
                                    // Get URI using FileProvider
                                    val fileUri = androidx.core.content.FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        exportFile
                                    )
                                    
                                    // Create intent to share the file
                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "application/json"
                                        putExtra(Intent.EXTRA_STREAM, fileUri)
                                        putExtra(Intent.EXTRA_SUBJECT, "菜式備份")
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    
                                    // Launch chooser
                                    context.startActivity(Intent.createChooser(shareIntent, "匯出菜式檔案"))
                                } catch (e: Exception) {
                                    // Fallback to text sharing if file export fails
                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "application/json"
                                        putExtra(Intent.EXTRA_TEXT, json)
                                        putExtra(Intent.EXTRA_SUBJECT, "菜式備份")
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "分享菜式備份"))
                                    Toast.makeText(context, "檔案匯出失敗，已改用文字分享", Toast.LENGTH_SHORT).show()
                                }
                                exportInProgress = false
                            }
                        }
                    },
                    trailing = {
                        if (exportInProgress) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                )
            }
            
            item {
                SettingsItem(
                    icon = Icons.Filled.Upload,
                    title = "匯入菜式",
                    subtitle = "從JSON文件匯入菜式資料",
                    onClick = {
                        // Show toast for now - import functionality to be implemented
                        Toast.makeText(context, "匯入功能即將推出", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            
            item {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            
            // About section
            item {
                SettingsCategory(title = "關於")
            }
            
            item {
                SettingsItem(
                    icon = Icons.Filled.Info,
                    title = "版本資訊",
                    subtitle = "Meal Randomizer v2.0.0",
                    onClick = { /* Show version dialog */ }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SettingsCategory(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isClickable = onClick != null
    
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(
                enabled = isClickable,
                onClick = { onClick?.invoke() }
            ),
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        },
        supportingContent = subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = trailing
    )
}