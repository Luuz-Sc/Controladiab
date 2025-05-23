plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.controladiab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.controladiab"
        minSdk = 29
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.biometric)
    implementation(libs.support.annotations)
    implementation(libs.room.common)
    implementation(libs.room.runtime.android)
    implementation(libs.window)
    implementation(libs.annotation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gridlayout)
    implementation(libs.gson)
    implementation(libs.tensorflow.lite)
    implementation(libs.slf4j.api)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout.v210)
    implementation(libs.work.runtime)
   }
}