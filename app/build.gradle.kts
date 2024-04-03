import java.io.FileInputStream
import java.io.OutputStream
import java.util.Properties

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

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))


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
            buildConfigField("Boolean", "DEBUG", "false")
            resValue("string", "AD_MOB_ID", properties["AD_MOB_ID"].toString())
            buildConfigField("String", "BANNER_ID", properties["BANNER_ID"].toString())
            buildConfigField("String", "OPEN_APP_ID", properties["OPEN_APP_ID"].toString())

        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("Boolean", "DEBUG", "true")
            resValue("string", "AD_MOB_ID", "\"ca-app-pub-3940256099942544~3347511713\"")
            buildConfigField("String", "BANNER_ID", "\"ca-app-pub-3940256099942544/9214589741\"")
            buildConfigField("String", "OPEN_APP_ID", "\"ca-app-pub-3940256099942544/9257395921\"")
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
    adMob()
    datastore()
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}