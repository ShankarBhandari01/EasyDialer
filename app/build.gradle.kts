plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
@Suppress("UnstableApiUsage")
android {
    namespace = "com.example.easydialer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easydialer"
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


        debug {
            isDebuggable = true
            isShrinkResources = false
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {
    implementation("androidx.activity:activity:1.8.0")
    val hiltVersion = rootProject.extra["hiltVersion"]
    val coroutine_version = "1.7.3"
    val lifecycle_version = "2.6.2"
    val retrofit_version = "2.9.0"
    val lottieVersion = "3.4.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.android.support:support-v4:28.0.0")
    implementation( "com.android.support:design:28.0.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //QuickPermissions-Kotlin
    implementation("com.github.quickpermissions:quickpermissions-kotlin:0.4.0")
    //EasyPermissions-ktx
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("androidx.activity:activity-ktx:1.8.1")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutine_version")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // Hilt testing dependencies
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    //Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
    //SwipeRefreshlayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //chuckerInterceptor
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
    //dataStore
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    kapt("androidx.room:room-compiler:2.6.1")
    //CustomActivityOnCrash
    implementation("cat.ereza:customactivityoncrash:2.4.0")
    // joda time
    implementation("net.danlew:android.joda:2.12.7")
    //sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // library for Play In-App Update:
    implementation("com.google.android.play:app-update-ktx:2.1.0")

    //lottie animation
    implementation("com.airbnb.android:lottie:$lottieVersion")


}

