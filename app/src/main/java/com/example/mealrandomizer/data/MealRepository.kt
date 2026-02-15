package com.example.mealrandomizer.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealDao: MealDao) {
    fun getAllMeals(): Flow<List<Meal>> = mealDao.getAll()

    suspend fun getMeal(id: Long): Meal? = mealDao.getById(id)

    suspend fun insertMeal(meal: Meal): Long = mealDao.insert(meal)

    suspend fun updateMeal(meal: Meal) = mealDao.update(meal)

    suspend fun deleteMeal(meal: Meal) = mealDao.delete(meal)

    fun getMealsByCategory(category: Category): Flow<List<Meal>> = mealDao.getByCategory(category)

    fun searchMeals(query: String): Flow<List<Meal>> = mealDao.search(query)

    suspend fun getRandomMeal(): Meal? = mealDao.getRandom()

    suspend fun getRandomMealExcluding(excludedIds: List<Long>): Meal? = mealDao.getRandomExcluding(excludedIds)

    suspend fun countMealsByName(name: String, excludeId: Long = 0): Int = mealDao.countByName(name, excludeId)
}