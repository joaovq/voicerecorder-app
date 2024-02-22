plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.room")
    id("com.google.dagger.hilt.android")
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "br.com.joaovq.voicerecorder"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.joaovq.voicerecorder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "DEBUG", "false")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "DEBUG", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    android()
    compose()
    test()
    room()
    hilt()
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}