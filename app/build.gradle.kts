plugins {
    alias(libs.plugins.android.application) // Подключение плагина Android Application
    alias(libs.plugins.kotlin.android)     // Подключение плагина Kotlin для Android
    alias(libs.plugins.kotlin.compose)     // Подключение плагина для Jetpack Compose
}

android {
    namespace = "com.example.otg_connect" // Пространство имён приложения
    compileSdk = 35                      // Версия SDK для компиляции

    defaultConfig {
        applicationId = "com.example.otg_connect" // Идентификатор приложения
        minSdk = 26                               // Минимальная поддерживаемая версия SDK
        targetSdk = 35                            // Целевая версия SDK
        versionCode = 1                           // Код версии приложения
        versionName = "1.0"                       // Имя версии приложения

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner для тестов
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Минификация отключена для релизной сборки
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), // Правила ProGuard
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Язык Java 11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11" // Kotlin будет использовать JVM версии 11
    }
    buildFeatures {
        compose = true // Включение Jetpack Compose
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)                      // Android KTX
    implementation(libs.androidx.lifecycle.runtime.ktx)         // Жизненный цикл
    implementation(libs.androidx.activity.compose)              // Activity для Compose
    implementation(platform(libs.androidx.compose.bom))         // BOM для Compose
    implementation(libs.androidx.ui)                            // UI-компоненты Compose
    implementation(libs.androidx.ui.graphics)                   // Графика для Compose
    implementation(libs.androidx.ui.tooling.preview)            // Предпросмотр Compose
    implementation(libs.androidx.material3)                     // Material Design 3
    implementation(libs.usbSerial)                              // Библиотека USB Serial
    testImplementation(libs.junit)                              // JUnit
    androidTestImplementation(libs.androidx.junit)              // AndroidJUnit
    androidTestImplementation(libs.androidx.espresso.core)      // Espresso
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM для тестов Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)     // Тестирование Compose
    debugImplementation(libs.androidx.ui.tooling)               // Инструменты отладки
    debugImplementation(libs.androidx.ui.test.manifest)         // Манифест для тестов
}