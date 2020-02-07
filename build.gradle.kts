import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.3.61"
    application
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "io.vertx.core.Launcher" // Main class
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    
    implementation("io.vertx:vertx-core:3.8.5")
    implementation("io.vertx:vertx-lang-kotlin:3.8.5")
    implementation("io.vertx:vertx-web:3.8.5")
}

val mainVerticleName = "com.example.vertxsample.MainVerticle" // Verticle for the entry point
val watchForChange = "src/**/*.kt" // Target files to trigger redeploy
val doOnChange = "${projectDir}/gradlew classes" // Task to be run on redeploy

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
    getByName<JavaExec>("run") {
        args = listOf("run", mainVerticleName, "--redeploy=${watchForChange}", "--launcher-class=${application.mainClassName}", "--on-redeploy=${doOnChange}")
    }
    withType<ShadowJar> {
        manifest {
            attributes["Main-Verticle"] = mainVerticleName
        }
        mergeServiceFiles {
            include("META-INF/services/io.vertx.core.spi.VerticleFactory")
        }
    }
}