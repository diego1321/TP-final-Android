plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android") version "1.9.23"
    id("org.jetbrains.kotlin.kapt") version "1.9.23"
}

android {
    namespace = "com.example.tp_final_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tp_final_android"
        minSdk = 24
        targetSdk = 36
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

    // 1. Decirle al compilador de JAVA que use Java 11
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // 2. Decirle al compilador de KOTLIN que use Java 11
    kotlinOptions {
        jvmTarget = "11"
    }

    // Opciones de Test (para las pruebas unitarias)
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

// 3. Decirle a KAPT que use Java 11
kapt {
    correctErrorTypes = true
    arguments {
        arg("kotlin.jvm.target", "11")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.recyclerview)

    // --- Dependencias de Room ---
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // --- Dependencias de Retrofit y Gson ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // --- Dependencias de Test ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Mockito
    testImplementation("org.mockito:mockito-core:4.8.0")
    androidTestImplementation("org.mockito:mockito-android:4.8.0")

    // Para probar LiveData
    testImplementation("androidx.arch.core:core-testing:2.2.0")
}