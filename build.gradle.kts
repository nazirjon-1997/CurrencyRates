// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
    id("androidx.navigation.safeargs") version "2.7.7" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}