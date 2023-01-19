pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.google.com") // Google's Maven repository
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}
rootProject.name = "BluetoothTest"
include(":app")
include(":higherground.lib.satpaq")

project(":higherground.lib.satpaq").projectDir = File("libs/lib.satpaq/higherground.lib.satpaq")

