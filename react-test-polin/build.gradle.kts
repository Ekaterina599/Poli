plugins {
    kotlin("multiplatform") version "1.7.10"
    id("io.kotest.multiplatform") version "5.5.1"
    application
}

application {
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

group = "ru.altmanea.edu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:2.0.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("org.slf4j:slf4j-api:2.0.3")
                implementation("org.slf4j:slf4j-simple:2.0.3")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            val kotlinWrappers = "org.jetbrains.kotlin-wrappers"
            val kotlinWrappersVersion = "1.0.0-pre.391"
            dependencies {
                implementation(project.dependencies.enforcedPlatform(
                    "$kotlinWrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
                implementation("$kotlinWrappers:kotlin-emotion")
                implementation("$kotlinWrappers:kotlin-react")
                implementation("$kotlinWrappers:kotlin-react-dom")
                implementation("$kotlinWrappers:kotlin-react-router-dom")
                implementation("$kotlinWrappers:kotlin-react-redux")            }
        }
        val jsTest by getting  {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:5.5.1")
                implementation("io.kotest:kotest-assertions-core:5.5.1")
            }
        }
    }
}

application {
    mainClass.set("ru.altmanea.edu.application.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}