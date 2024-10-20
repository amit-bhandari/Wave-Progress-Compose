plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}
/**
 * Here is the Reference which i followed
 * @see "https://medium.com/simform-engineering/publishing-library-in-maven-central-part-1-994c5fe0c004"
 */


android {
    namespace = "com.bhandari.wave_progress"
    compileSdk = 34

    // configure the variant for now amit sir i am doing for testing .

    publishing {
        // https://developer.android.com/build/publish-library/configure-pub-variants
        singleVariant("debug")
       // withSourceJar()
    }


    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

afterEvaluate {
    publishing{
        publications{
            create<MavenPublication>("debug") {
                from(components["debug"])
                groupId = "com.bhandari"
                artifactId = "wave_progress"
                version = "1.0.0"

                pom {
                    packaging = "aar" // because of android library
                    name.set("wave_progress")
                    description.set("Wave Progress: Library for compose android application")
                    url.set("https://github.com/amit-bhandari/Wave-Progress-Compose")
                    inceptionYear.set("2024")


                    licenses {
                        licenses {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("amit-bhandari")
                            name.set("Amit Bhandari")
                            email.set("amit.bhandari.c@gmail.com")
                        }
                    }


                    scm {
                        /**
                         *  This field specifies how to connect to the SCM repository.
                         * It usually includes the protocol (like git, https, or svn) and the repository URL.
                         * This URL is used for read-only access.
                         */
                        connection.set("scm:git@github.com/amit-bhandari/Wave-Progress-Compose")

                        /**
                         * This field is similar to the connection field but is meant for developers who need read-write access to the repository.
                         * It typically contains a URL that allows developers to push changes.
                         */
                        developerConnection.set("scm:git@github.com/amit-bhandari/Wave-Progress-Compose.git")

                        /**
                         * This field provides the web URL for the SCM repository.
                         * It is often a link to the project's GitHub, Bitbucket,
                         * or any other hosting service where the source code can be viewed.
                         *
                         */
                        url.set("https://github.com/amit-bhandari/Wave-Progress-Compose")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.test.manifest)
    
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}