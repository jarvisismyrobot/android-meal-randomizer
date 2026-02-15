package com.example.mealrandomizer.di

import android.content.Context
import com.example.mealrandomizer.data.AppDatabase
import com.example.mealrandomizer.data.MealPlanDao
import com.example.mealrandomizer.data.MealPlanRepository
import com.example.mealrandomizer.data.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): AppDatabase {
        return AppDatabase.getDatabase(context, scope)
    }

    @Provides
    @Singleton
    fun provideMealDao(db: AppDatabase) = db.mealDao()

    @Provides
    @Singleton
    fun provideMealRepository(mealDao: com.example.mealrandomizer.data.MealDao) =
        MealRepository(mealDao)

    @Provides
    @Singleton
    fun provideMealPlanDao(db: AppDatabase) = db.mealPlanDao()

    @Provides
    @Singleton
    fun provideMealPlanRepository(mealPlanDao: MealPlanDao, mealDao: com.example.mealrandomizer.data.MealDao) =
        MealPlanRepository(mealPlanDao, mealDao)
}