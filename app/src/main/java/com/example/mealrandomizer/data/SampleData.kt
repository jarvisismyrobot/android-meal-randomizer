package com.example.mealrandomizer.data

val sampleMeals = listOf(
    // 早餐點心 (1-25)
    Meal(
        name = "腸粉",
        description = "傳統港式腸粉，滑嫩可口",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.VEGETARIAN)
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
        name = "蝦餃",
        description = "水晶蝦餃",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
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
        name = "糯米雞",
        description = "荷葉包裹糯米雞",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 600,
        categories = listOf(Category.BREAKFAST, Category.HEARTY)
    ),
    Meal(
        name = "叉燒包",
        description = "蜜汁叉燒包",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 250,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "流沙包",
        description = "奶黃流沙包",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 20,
        calories = 280,
        categories = listOf(Category.BREAKFAST, Category.DESSERT)
    ),
    Meal(
        name = "皮蛋瘦肉粥",
        description = "皮蛋瘦肉粥",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 40,
        calories = 300,
        categories = listOf(Category.BREAKFAST, Category.HEARTY)
    ),
    Meal(
        name = "及第粥",
        description = "豬雜粥",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 350,
        categories = listOf(Category.BREAKFAST, Category.HEARTY)
    ),
    Meal(
        name = "煎堆",
        description = "芝麻煎堆",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 320,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "鹹水角",
        description = "脆皮鹹水角",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 280,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "春卷",
        description = "炸春卷",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 300,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "蘿蔔糕",
        description = "煎蘿蔔糕",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 25,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.VEGETARIAN)
    ),
    Meal(
        name = "馬拉糕",
        description = "鬆軟馬拉糕",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 280,
        categories = listOf(Category.BREAKFAST, Category.DESSERT)
    ),
    Meal(
        name = "雞蛋仔",
        description = "港式雞蛋仔",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 350,
        categories = listOf(Category.BREAKFAST, Category.SNACK)
    ),
    Meal(
        name = "格仔餅",
        description = "窩夫格仔餅",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 400,
        categories = listOf(Category.BREAKFAST, Category.DESSERT)
    ),
    Meal(
        name = "炒麵",
        description = "港式炒麵",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 500,
        categories = listOf(Category.BREAKFAST, Category.LUNCH)
    ),
    Meal(
        name = "豬腸粉",
        description = "豉油豬腸粉",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 250,
        categories = listOf(Category.BREAKFAST, Category.VEGETARIAN)
    ),
    Meal(
        name = "豆漿",
        description = "新鮮豆漿",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 80,
        categories = listOf(Category.BREAKFAST, Category.HEALTHY)
    ),

    // 午餐主食 (26-50)
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
        name = "魚蛋粉",
        description = "魚蛋配河粉",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER)
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
        name = "蒸排骨",
        description = "豆豉蒸排骨",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER)
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
        name = "鹹魚雞粒炒飯",
        description = "鹹香炒飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 650,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "揚州炒飯",
        description = "蝦仁火腿炒飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 600,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "星洲炒米",
        description = "咖喱炒米粉",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 550,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "滑蛋蝦仁飯",
        description = "滑蛋蝦仁蓋飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 500,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "豉椒排骨飯",
        description = "豉椒排骨蓋飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 600,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "粟米肉粒飯",
        description = "粟米肉粒蓋飯",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 450,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "咖喱雞飯",
        description = "咖喱雞配白飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 650,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "番茄牛肉飯",
        description = "番茄牛肉蓋飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 500,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "麻婆豆腐飯",
        description = "麻辣豆腐蓋飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 450,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "海南雞飯",
        description = "海南雞配油飯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 60,
        calories = 700,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "肉醬意粉",
        description = "意大利肉醬意粉",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 550,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "焗豬扒飯",
        description = "番茄焗豬扒飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 750,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "焗芝士海鮮飯",
        description = "芝士焗海鮮飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 700,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "炸雞髀飯",
        description = "炸雞腿配白飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 800,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "燒味雙拼飯",
        description = "燒味雙拼配白飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 20,
        calories = 700,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "鹹蛋肉餅飯",
        description = "鹹蛋蒸肉餅飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 650,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "豉汁蒸鱔飯",
        description = "豉汁蒸白鱔飯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 45,
        calories = 600,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "魚香茄子飯",
        description = "魚香茄子蓋飯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 450,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN)
    ),

    // 晚餐主菜 (51-75)
    Meal(
        name = "煲仔飯",
        description = "臘味煲仔飯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 60,
        calories = 800,
        categories = listOf(Category.DINNER, Category.HEARTY)
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
        name = "燒鵝",
        description = "脆皮燒鵝",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 120,
        calories = 700,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "梅菜扣肉",
        description = "梅菜扣豬肉",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 750,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "咕嚕肉",
        description = "甜酸咕嚕肉",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 600,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "椒鹽鮮魷",
        description = "椒鹽炸鮮魷",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 450,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "京都排骨",
        description = "甜酸京都排骨",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 650,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "清蒸海鮮",
        description = "清蒸魚或蝦",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 400,
        categories = listOf(Category.DINNER, Category.HEALTHY)
    ),
    Meal(
        name = "避風塘炒蟹",
        description = "蒜香炒蟹",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 45,
        calories = 550,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "薑蔥炒蟹",
        description = "薑蔥炒肉蟹",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 40,
        calories = 500,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "椒鹽豆腐",
        description = "椒鹽炸豆腐",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 300,
        categories = listOf(Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "瑤柱扒時蔬",
        description = "瑤柱扒菜心",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 250,
        categories = listOf(Category.DINNER, Category.VEGETARIAN, Category.HEALTHY)
    ),
    Meal(
        name = "蒜蓉炒時蔬",
        description = "蒜蓉炒菜心或芥蘭",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 150,
        categories = listOf(Category.DINNER, Category.VEGETARIAN, Category.HEALTHY)
    ),
    Meal(
        name = "羅漢齋",
        description = "雜錦素菜",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 300,
        categories = listOf(Category.DINNER, Category.VEGETARIAN, Category.HEALTHY)
    ),
    Meal(
        name = "咖喱牛腩",
        description = "咖喱煮牛腩",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 700,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "枝竹羊腩煲",
        description = "枝竹燜羊腩",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 120,
        calories = 800,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "粟米魚塊",
        description = "粟米汁魚塊",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 450,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "椒絲腐乳通菜",
        description = "腐乳炒通菜",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 200,
        categories = listOf(Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "鹹蛋黃炒蝦",
        description = "鹹蛋黃炒蝦球",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 500,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "豉汁蒸排骨",
        description = "豉汁蒸排骨",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 400,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "粟米斑塊",
        description = "粟米汁石斑塊",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 500,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "紅燒豆腐",
        description = "紅燒豆腐煲",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 25,
        calories = 250,
        categories = listOf(Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "豉椒炒蜆",
        description = "豉椒炒花甲",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 350,
        categories = listOf(Category.DINNER)
    ),
    Meal(
        name = "清炒時蔬",
        description = "清炒菜心",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 120,
        categories = listOf(Category.DINNER, Category.VEGETARIAN, Category.HEALTHY)
    ),

    // 湯品及小食 (76-100)
    Meal(
        name = "蛋撻",
        description = "酥皮蛋撻",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 250,
        categories = listOf(Category.DESSERT, Category.SNACK)
    ),
    Meal(
        name = "咖喱魚蛋",
        description = "咖喱汁煮魚蛋",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 300,
        categories = listOf(Category.SNACK, Category.LUNCH)
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
        name = "齋滷味",
        description = "素食滷味",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 60,
        calories = 250,
        categories = listOf(Category.VEGETARIAN, Category.HEALTHY, Category.SNACK)
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
        name = "腐竹糖水",
        description = "傳統糖水",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 40,
        calories = 200,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "紅豆沙",
        description = "陳皮紅豆沙",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 60,
        calories = 250,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "芝麻糊",
        description = "芝麻糊甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 300,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "花生糊",
        description = "花生糊甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 50,
        calories = 350,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "杏仁茶",
        description = "杏仁茶甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 200,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "番薯糖水",
        description = "薑汁番薯糖水",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 45,
        calories = 250,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "豆腐花",
        description = "甜豆腐花",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 30,
        calories = 150,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "燉蛋",
        description = "燉鮮奶或雞蛋",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 200,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "燉雪梨",
        description = "冰糖燉雪梨",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 60,
        calories = 180,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "木瓜雪耳糖水",
        description = "木瓜雪耳甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 50,
        calories = 220,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "綠豆沙",
        description = "陳皮綠豆沙",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 70,
        calories = 230,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "合桃露",
        description = "合桃露甜品",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 55,
        calories = 320,
        categories = listOf(Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "鳳凰卷",
        description = "雞蛋卷",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 90,
        calories = 400,
        categories = listOf(Category.DESSERT, Category.SNACK)
    ),
    Meal(
        name = "老婆餅",
        description = "冬瓜蓉老婆餅",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 120,
        calories = 350,
        categories = listOf(Category.DESSERT, Category.SNACK)
    ),
    Meal(
        name = "雞仔餅",
        description = "南乳雞仔餅",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 100,
        calories = 450,
        categories = listOf(Category.SNACK)
    ),
    Meal(
        name = "笑口棗",
        description = "芝麻笑口棗",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 300,
        categories = listOf(Category.SNACK)
    ),
    Meal(
        name = "牛耳",
        description = "南乳牛耳",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 80,
        calories = 400,
        categories = listOf(Category.SNACK)
    ),
    Meal(
        name = "蛋散",
        description = "糖漿蛋散",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 380,
        categories = listOf(Category.SNACK, Category.DESSERT)
    ),
    Meal(
        name = "糖不甩",
        description = "花生芝麻糯米糍",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 350,
        categories = listOf(Category.DESSERT, Category.SNACK)
    ),
    Meal(
        name = "缽仔糕",
        description = "紅豆缽仔糕",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 280,
        categories = listOf(Category.SNACK, Category.DESSERT)
    ),

    // 額外菜式 (101-129)
    Meal(
        name = "燉湯",
        description = "老火燉湯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 180,
        calories = 200,
        categories = listOf(Category.DINNER, Category.HEALTHY)
    ),
    Meal(
        name = "冬瓜盅",
        description = "冬瓜燉湯",
        difficulty = Difficulty.HARD,
        cookingTimeMinutes = 150,
        calories = 180,
        categories = listOf(Category.DINNER, Category.HEALTHY)
    ),
    Meal(
        name = "粟米湯",
        description = "粟米羹",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 25,
        calories = 150,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "酸辣湯",
        description = "酸辣湯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 120,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "羅宋湯",
        description = "羅宋湯",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 60,
        calories = 200,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "忌廉湯",
        description = "忌廉蘑菇湯",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 250,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "炒時蔬",
        description = "蒜蓉炒菜",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 100,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "蒸水蛋",
        description = "滑嫩蒸水蛋",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 120,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "煎釀三寶",
        description = "煎釀青椒茄子豆腐",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "炸雲吞",
        description = "炸鮮蝦雲吞",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 400,
        categories = listOf(Category.SNACK, Category.LUNCH)
    ),
    Meal(
        name = "春卷",
        description = "炸春卷",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 30,
        calories = 300,
        categories = listOf(Category.SNACK, Category.LUNCH)
    ),
    Meal(
        name = "煎餃子",
        description = "煎豬肉餃子",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 35,
        calories = 450,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "水餃",
        description = "豬肉白菜水餃",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 400,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "炒飯",
        description = "簡單炒飯",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 500,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "炒麵",
        description = "簡單炒麵",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 550,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "湯麵",
        description = "簡單湯麵",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 400,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "拌麵",
        description = "麻醬拌麵",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 15,
        calories = 450,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "粥",
        description = "白粥",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 60,
        calories = 150,
        categories = listOf(Category.BREAKFAST, Category.HEALTHY)
    ),
    Meal(
        name = "湯飯",
        description = "湯泡飯",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 20,
        calories = 350,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "煲仔菜",
        description = "砂鍋菜",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 45,
        calories = 500,
        categories = listOf(Category.DINNER, Category.HEARTY)
    ),
    Meal(
        name = "燜麵",
        description = "燜麵條",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 40,
        calories = 600,
        categories = listOf(Category.LUNCH, Category.DINNER)
    ),
    Meal(
        name = "涼拌菜",
        description = "涼拌青瓜",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 80,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN)
    ),
    Meal(
        name = "沙拉",
        description = "蔬菜沙拉",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 5,
        calories = 100,
        categories = listOf(Category.LUNCH, Category.DINNER, Category.VEGETARIAN, Category.HEALTHY)
    ),
    Meal(
        name = "水果拼盤",
        description = "新鮮水果",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 150,
        categories = listOf(Category.BREAKFAST, Category.DESSERT, Category.HEALTHY)
    ),
    Meal(
        name = "果汁",
        description = "鮮榨果汁",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 5,
        calories = 120,
        categories = listOf(Category.BREAKFAST, Category.HEALTHY)
    ),
    Meal(
        name = "酸奶",
        description = "原味酸奶",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 0,
        calories = 100,
        categories = listOf(Category.BREAKFAST, Category.HEALTHY)
    ),
    Meal(
        name = "麥片",
        description = "牛奶麥片",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 5,
        calories = 200,
        categories = listOf(Category.BREAKFAST, Category.HEALTHY)
    ),
    Meal(
        name = "三文治",
        description = "火腿三文治",
        difficulty = Difficulty.EASY,
        cookingTimeMinutes = 10,
        calories = 350,
        categories = listOf(Category.BREAKFAST, Category.LUNCH)
    ),
    Meal(
        name = "漢堡包",
        description = "牛肉漢堡",
        difficulty = Difficulty.MEDIUM,
        cookingTimeMinutes = 25,
        calories = 600,
        categories = listOf(Category.LUNCH, Category.DINNER)
    )
)