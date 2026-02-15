package com.example.mealrandomizer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY name")
    fun getAll(): Flow<List<Meal>>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getById(id: Long): Meal?

    @Insert
    suspend fun insert(meal: Meal): Long

    @Update
    suspend fun update(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM meals WHERE categories LIKE '%' || :category || '%'")
    fun getByCategory(category: Category): Flow<List<Meal>>
    
    @Query("SELECT * FROM meals WHERE categories LIKE '%' || :category || '%'")
    suspend fun getByCategoryList(category: Category): List<Meal>

    @Query("SELECT * FROM meals WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Meal>>

    @Query("SELECT * FROM meals ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandom(): Meal?

    @Query("SELECT * FROM meals WHERE id NOT IN (:excludedIds) ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomExcluding(excludedIds: List<Long>): Meal?

    @Query("SELECT * FROM meals LIMIT 1")
    suspend fun getAny(): Meal?

    // Check if a meal with the same name exists (case-insensitive, ignoring current meal)
    @Query("SELECT COUNT(*) FROM meals WHERE LOWER(TRIM(name)) = LOWER(TRIM(:name)) AND id != :excludeId")
    suspend fun countByName(name: String, excludeId: Long = 0): Int
}