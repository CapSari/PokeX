plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.dve.sari.minipokedex"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom.v20240400))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // Retrofit - HTTP client for API calls
    implementation(libs.retrofit)
    implementation(libs.converter.moshi) // If you're using Moshi with Retrofit
    implementation(libs.converter.gson)  // If you're using Gson with Retrofit
    implementation(libs.slack.eithernet)
    implementation(libs.javax.annotation.api)
    implementation(libs.javax.inject)
    implementation(libs.hilt.android) // Ensure this is the correct version
    implementation(libs.androidx.foundation)
    implementation(project(":core:networking"))
    implementation("androidx.compose.ui:ui")
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(project(":core:theme"))
    kapt(libs.hilt.compiler) // Ensure kapt is applied for Hilt
    implementation(libs.javapoet)
    implementation (libs.timber)

    implementation(libs.androidx.material3.android)
    implementation (libs.material3)
    implementation(libs.coil.compose)
    implementation(libs.material3)

    testImplementation(libs.junit)
    testImplementation ("io.mockk:mockk:1.13.7")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
