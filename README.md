# Meal Randomizer (煮乜食 App)

A full-featured Android app for randomly selecting meals, specifically designed for Hong Kong common dishes.

## Features

- **Pre-loaded 30 HK common meals** categorized for breakfast, lunch, dinner
- **Add, edit, delete recipes** with details: name, description, difficulty, cooking time, calories, categories (multi‑select)
- **User settings** for planning 1‑7 days
- **Random generation** with diversity (no repeats)
- **Meal history**, share, search
- **Local storage** using Room SQLite database
- **Notifications** (optional) via WorkManager
- **Modern UI** built with Jetpack Compose

## Project Structure

- Kotlin + Jetpack Compose
- Room for local database
- Hilt for dependency injection
- Navigation Compose
- DataStore for preferences
- WorkManager for notifications

## APK Output

The debug APK will be generated at:

`/home/admin/.openclaw/workspace/android-meal-randomizer/app/build/outputs/apk/debug/app-debug.apk`

## How to Install

1. Connect an Android device with USB debugging enabled.
2. Run:
   ```bash
   adb install /home/admin/.openclaw/workspace/android-meal-randomizer/app/build/outputs/apk/debug/app-debug.apk
   ```
3. Or use Android Studio to build and run directly.

## Build Status

The Gradle build is currently downloading dependencies. Once completed, the APK will be available at the above path.

## Notes

- All required screens (Home, Add/Edit, History, Settings, Search) are implemented.
- Sample data includes 30 common HK meals.
- The database is pre‑populated on first launch.
- The app uses Material Design 3 theming.

## Future Enhancements

- Share meal via intent
- Cloud sync
- Meal photos
- Shopping list generation