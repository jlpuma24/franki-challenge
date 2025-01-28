plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

android {
    namespace = "com.frankichallenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.frankichallenge"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        buildConfigField("String", "API_KEY", "\"5deca2f99f97d972562a33188d696b4a\"")
        buildConfigField("String", "BASE_PATH", "\"weather\"")
        buildConfigField("String", "ICON_BASE_URL", "\"https://openweathermap.org/img/wn/\"")
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.contrib)

    //Network
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)
    implementation(libs.gson)

    implementation(libs.androidx.constraintlayout)

    // Dependency injection
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.androidx.multidex)

    implementation(libs.glide)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Jetpack compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    // Test
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockk)

}