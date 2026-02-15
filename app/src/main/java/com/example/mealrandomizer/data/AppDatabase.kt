package com.example.mealrandomizer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Meal::class, MealPlan::class, MealPlanEntry::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun mealPlanDao(): MealPlanDao

    private class MealDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.mealDao())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Step 1: Delete duplicate meals, keeping the one with the smallest id
                db.execSQL("""
                    DELETE FROM meals 
                    WHERE id IN (
                        SELECT m2.id 
                        FROM meals m1 
                        JOIN meals m2 ON m1.name = m2.name 
                        WHERE m1.id < m2.id
                    )
                """.trimIndent())
                
                // Step 2: Create unique index on name column
                db.execSQL("CREATE UNIQUE INDEX index_meals_name ON meals(name)")
            }
        }
        
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create new table without difficulty column
                db.execSQL("""
                    CREATE TABLE meals_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        description TEXT NOT NULL,
                        cookingTimeMinutes INTEGER NOT NULL,
                        calories INTEGER,
                        categories TEXT NOT NULL,
                        createdAt INTEGER NOT NULL,
                        lastGenerated INTEGER
                    )
                """.trimIndent())
                
                // Copy data from old table, excluding difficulty column
                db.execSQL("""
                    INSERT INTO meals_new (id, name, description, cookingTimeMinutes, calories, categories, createdAt, lastGenerated)
                    SELECT id, name, description, cookingTimeMinutes, calories, categories, createdAt, lastGenerated
                    FROM meals
                """.trimIndent())
                
                // Drop old table
                db.execSQL("DROP TABLE meals")
                
                // Rename new table
                db.execSQL("ALTER TABLE meals_new RENAME TO meals")
                
                // Recreate unique index
                db.execSQL("CREATE UNIQUE INDEX index_meals_name ON meals(name)")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meal_randomizer_db"
                ).addCallback(MealDatabaseCallback(scope))
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Ensure database is populated on first open
                scope.launch(Dispatchers.IO) {
                    populateDatabase(instance.mealDao())
                }
                instance
            }
        }

        suspend fun populateDatabase(mealDao: MealDao) {
            // Only populate if database is empty
            if (mealDao.getAny() == null) {
                sampleMeals.forEach { mealDao.insert(it) }
            }
        }
    }
}