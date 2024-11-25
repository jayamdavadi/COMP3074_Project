plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ca.gbc.comp3074.restaurantguide"
    compileSdk = 34

    defaultConfig {
        applicationId = "ca.gbc.comp3074.restaurantguide"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Room Database dependencies
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("com.google.android.libraries.places:places:2.6.0")

    // Lifecycle dependencies
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
