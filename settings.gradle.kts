// Файл настройки проекта settings.gradle.kts. Здесь определяются репозитории и общие параметры для всего проекта.

pluginManagement {
    repositories {
        // Репозиторий Google для плагинов Android и связанных библиотек
        google {
            content {
                // Включаем группы пакетов, относящиеся к Android и Google
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        // Основной репозиторий Maven для общедоступных Java/Android библиотек
        mavenCentral()
        // Gradle Plugin Portal для загрузки плагинов Gradle
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Разрешаем использовать только репозитории, указанные в этом файле, и блокируем локальные настройки модулей
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Репозиторий Google для Android библиотек
        google()
        // Основной репозиторий Maven для общедоступных библиотек
        mavenCentral()
        // Репозиторий JitPack для сторонних библиотек, таких как usb-serial-for-android
        maven(url = "https://jitpack.io")
    }
}

// Установка имени основного проекта
rootProject.name = "OTG_connect"
// Подключение модуля приложения (app)
include(":app")