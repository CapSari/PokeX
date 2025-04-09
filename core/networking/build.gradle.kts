plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android") version "2.48" // Apply Hilt plugin here
    kotlin("kapt") // Apply the KAPT plugin for Hilt
}

android {
    namespace = "com.dve.sari.networking"
    compileSdk = 34

    buildFeatures {
        buildConfig = true // Enable BuildConfig feature
    }

    defaultConfig {
        minSdk = 24
        buildConfigField("boolean", "DEBUG_MODE", "true") // Your custom BuildConfig field

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    // Hilt dependencies
    implementation(libs.hilt.android) // Make sure this is the correct version
    kapt(libs.hilt.compiler) // Ensure kapt is applied for Hilt

    // Kotlin Coroutines for async tasks
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit - HTTP client for API calls
    implementation(libs.retrofit)
    implementation(libs.converter.moshi) // If you're using Moshi with Retrofit
    implementation(libs.converter.gson)  // If you're using Gson with Retrofit

    // Moshi - JSON parsing library (optional if using Moshi)
    implementation(libs.moshi.kotlin)

    // OkHttp - Networking library used by Retrofit
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor) // For logging network requests

    // Slack Eithernet - Networking
    implementation(libs.slack.eithernet)
    implementation(libs.javapoet)
    implementation(libs.androidx.compose.bom.v20240400)

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
