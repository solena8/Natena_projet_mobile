import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    android.buildFeatures.buildConfig = true
    namespace = "com.example.natena"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.natena"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //@Todo ajouter fichier local.properties
        val secrets = Properties()
        val localProperties = rootProject.file("local.properties")
        if (localProperties.exists()) {
            localProperties.inputStream().use {
                secrets.load(it)
            }
        } else {
            println("Le fichier local.properties n'existe pas.")
        }
        buildConfigField("String", "BASE_ID", "\"${secrets["BASE_ID"]}\"")
        buildConfigField("String", "TABLE_ID", "\"${secrets["TABLE_ID"]}\"")
        buildConfigField("String", "API_KEY", "\"${secrets["API_KEY"]}\"")
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.glide)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}