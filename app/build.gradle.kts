import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

// Read local properties for dev keys
val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

val appKey = project.findProperty("appKey") ?: localProperties["appKey"]

android {
    namespace = Configuration.applicationId
    compileSdk = Configuration.compileSdk

    kapt {
        javacOptions {
            option("--target", "8")
        }
    }

    defaultConfig {
        applicationId = Configuration.applicationId
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        buildConfigField("String", "APP_KEY", "\"$appKey\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = Configuration.jvmTarget
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    api(project(":core-api"))
    implementation(project(":core-impl"))

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.composeActivity)
    implementation(Libs.AndroidX.lifecycleComposeRuntime)
    implementation(Libs.AndroidX.lifecycleComposeViewModel)
    implementation(Libs.AndroidX.navigationCompose)
    implementation(Libs.AndroidX.hiltNavigationCompose)

    implementation(Libs.Coroutines.android)

    implementation(Libs.Hilt.hiltAndroid)
    kapt(Libs.Hilt.hiltCompiler)

    implementation(Libs.Android.material)

    implementation(platform(Libs.Compose.composeBom))
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.material3)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Compose.toolingPreview)

    testImplementation(Libs.junit)
    testImplementation(Libs.mockk)
    testImplementation(Libs.Kotlin.testCommon)
    testImplementation(Libs.Kotlin.testJunit)
    testImplementation(Libs.Coroutines.test)
    testImplementation(Libs.appmattusFixtures)

}
