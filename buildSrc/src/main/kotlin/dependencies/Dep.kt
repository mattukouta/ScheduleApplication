package dependencies

object Dep {
    private object Version {
        const val kotlin = "1.4.21"
        const val hilt = "2.31-alpha"
        const val androidHilt = "1.0.0-alpha03"
        const val lifecycle = "2.2.0"
        const val room = "2.2.6"
        const val navigation = "2.3.2"
        const val coroutine = "1.4.2"
        const val mockk = "1.9.3"
    }

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.1.2"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
        const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
    }

    object AndroidX {
        const val coreKTX = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.3.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val activity = "androidx.activity:activity-ktx:1.1.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.2.5"

        object Lifecycle {
            const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
            const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
            const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
        }

        object Room {
            const val room = "androidx.room:room-ktx:${Version.room}"
            const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
            const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
        }

        object Dagger {
            const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
            const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
            const val hiltLifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.androidHilt}"
            const val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.androidHilt}"
        }

        object Navigation {
            const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
            const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
            const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Version.navigation}"
        }
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

        object Coroutine {
            const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
            const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutine}"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val androidTestCore = "androidx.test:core:1.3.0"
        const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine}"

        object Mockk {
            const val mockk = "io.mockk:mockk:${Version.mockk}"
        }
    }

    object AndroidTest {
        const val androidJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}