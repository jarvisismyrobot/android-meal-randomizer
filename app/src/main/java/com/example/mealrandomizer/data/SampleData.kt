package com.example.mealrandomizer.data

val sampleMeals = listOf(
    Meal(
        name = "腸粉",
        description = "傳統港式腸粉，滑嫩可口",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.VEGETARIAN)
    ),
    Meal(
        name = "叉燒飯",
        description = "蜜汁叉燒配白飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 650,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "雲吞麵",
        description = "鮮蝦雲吞配幼麵",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 400,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "燒賣",
        description = "經典點心",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 150,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "菠蘿油",
        description = "菠蘿包夾牛油",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 350,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "奶茶",
        description = "港式絲襪奶茶",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 120,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "蛋撻",
        description = "酥皮蛋撻",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 250,
        categories = listOf(Category.DESSERT, Category.SNACK)
    ),
    Meal(
        name = "煲仔飯",
        description = "臘味煲仔飯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 60,
        calories = 800,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "炒麵",
        description = "港式炒麵",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 500,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "魚蛋粉",
        description = "魚蛋配河粉",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "豉油雞",
        description = "滷水豉油雞",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 600,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "白切雞",
        description = "嫩滑白切雞",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 50,
        calories = 550,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "蝦餃",
        description = "水晶蝦餃",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 60,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "燒鵝",
        description = "脆皮燒鵝",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 120,
        calories = 700,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "乾炒牛河",
        description = "牛肉炒河粉",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 750,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "西多士",
        description = "法式吐司",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 450,
        categories = listOf(Category.BREAKFAST, Category.DESSERT)
    ),
    Meal(
        name = "咖喱魚蛋",
        description = "咖喱汁煮魚蛋",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 300,
        categories = listOf(Category.SNACK)
    ),
    Meal(
        name = "蒸排骨",
        description = "豆豉蒸排骨",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "楊枝甘露",
        description = "芒果柚子甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 300,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "碗仔翅",
        description = "仿魚翅湯羹",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 250,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "車仔麵",
        description = "自選配料麵食",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 500,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "沙爹牛肉麵",
        description = "沙爹醬牛肉麵",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 550,
        categories = listOf(Category.BREAKFAST, Category.LUNCH)
    ),
    Meal(
        name = "鴛鴦",
        description = "咖啡奶茶混合",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 100,
        categories = listOf(Category.BREAKFAST)
    ),
    Meal(
        name = "炸兩",
        description = "腸粉包油條",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 350,
        categories = listOf(Category.BREAKFAST)
    ),
    Meal(
        name = "鹹煎餅",
        description = "香脆鹹味煎餅",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 300,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "齋滷味",
        description = "素食滷味",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 60,
        calories = 250,
        categories = listOf(Category.VEGETARIAN, Category.HEALTHY)
    ),
    Meal(
        name = "薑汁撞奶",
        description = "溫熱甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 200,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "糯米雞",
        description = "荷葉包裹糯米雞",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 600,
        categories = listOf(Category.BREAKFAST, Category.HEARTY)
    ),
    Meal(
        name = "鹹魚雞粒炒飯",
        description = "鹹香炒飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 650,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "腐竹糖水",
        description = "傳統糖水",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 40,
        calories = 200,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    )
)