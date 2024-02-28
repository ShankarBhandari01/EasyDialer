buildscript {
    extra.apply {
        set("kotlinVersion", "1.9.10")
        set("hiltVersion", "2.48.1")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlinVersion = rootProject.extra["kotlinVersion"]
        val hiltVersion = rootProject.extra["hiltVersion"]

        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}


tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
