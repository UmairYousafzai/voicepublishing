pluginManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven { setUrl( "https://jitpack.io") }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()

        mavenCentral()
        maven { setUrl( "https://jitpack.io") }
    }
}

rootProject.name = "voicepublishing"
include(":app")
 