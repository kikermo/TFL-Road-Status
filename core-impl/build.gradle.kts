plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(project(":core-api"))

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.KotlinX.serialisation)
    implementation(Libs.Dagger.inject)
    implementation(Libs.retrofit)
}
