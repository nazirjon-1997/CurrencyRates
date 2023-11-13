// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("androidx.navigation.safeargs") version "2.7.5" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}