plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    // Adicione a dependência para o plug-in Gradle do Performance Monitoring
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    // Adicione o plug-in Gradle de monitoramento de desempenho

}

android {
    namespace = "com.example.projetofirebase3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projetofirebase3"
        minSdk = 21
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.firebase:firebase-database:20.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(kotlin("script-runtime"))
    implementation("com.google.firebase:firebase-appcheck-ktx")
    implementation("com.google.firebase:firebase-database")

    // Importe a BoM para a plataforma Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    //Importa a BoM para a plataforma Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    //Adiciona a dependência para a biblioteca do Performance Monitoring
    // Ao usar a BoM, você não especifica versões nas dependências da biblioteca do Firebase
    implementation("com.google.firebase:firebase-perf")

    // Declara a dependência da biblioteca Cloud Firestore
    // Ao usar a BoM, você não especifica versões nas dependências da biblioteca do Firebase
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-installations")

    //Importa a BoM para a plataforma Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    //Adiciona a dependência para a biblioteca do Performance Monitoring
    // Ao usar a BoM, você não especifica versões nas dependências da biblioteca do Firebase
    implementation("com.google.firebase:firebase-perf")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
}