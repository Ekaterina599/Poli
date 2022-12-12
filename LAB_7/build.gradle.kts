plugins {
    kotlin("js") version "1.7.10"
}

group = "me.ekate"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}
val kotlinWrappers = "org.jetbrains.kotlin-wrappers"
val kotlinWrappersVersion = "1.0.0-pre.391"

dependencies {
    testImplementation(kotlin("test"))
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("$kotlinWrappers:kotlin-emotion")
    implementation("$kotlinWrappers:kotlin-react")
    implementation("$kotlinWrappers:kotlin-react-dom")
    implementation("$kotlinWrappers:kotlin-react-router-dom")
    implementation("$kotlinWrappers:kotlin-react-redux")

}


kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}