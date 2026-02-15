package com.example.mealrandomizer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun appLaunchesWithoutCrash() {
        // Launch the main activity
        ActivityScenario.launch(MainActivity::class.java).use {
            // If we reach here without crash, test passes
            // Wait a moment to ensure UI loads
            Thread.sleep(2000)
        }
    }
}