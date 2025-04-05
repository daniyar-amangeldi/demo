pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/daniyar-amangeldi/networkkit")
            credentials {
                username = "daniyar-amangeldi"
                password = "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"
            }
        }
    }
}

rootProject.name = "demo"
include(":app")
include(":domain")
include(":data")
