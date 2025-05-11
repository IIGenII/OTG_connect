// Верхнеуровневый файл сборки, где можно добавить общие настройки для всех модулей/подпроектов
plugins {
    alias(libs.plugins.android.application) apply false // Плагин Android Application
    alias(libs.plugins.kotlin.android) apply false      // Плагин Kotlin для Android
    alias(libs.plugins.kotlin.compose) apply false      // Плагин Compose
}

// Плагин apply false означает, что эти плагины не применяются на уровне проекта,
// а будут применяться только в модулях (например, в модуле app).