buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.androidGradle)
        classpath(Plugins.kotlinGradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}