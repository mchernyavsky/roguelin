plugins {
    application
    kotlin("jvm") version "1.3.72"
}

group = "io.github.mchernyavsky"
version = "0.1.1"

application {
    mainClass.set("io.github.mchernyavsky.roguelin.ApplicationMainKt")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.trystan:AsciiPanel:master-SNAPSHOT")
    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("org.slf4j:slf4j-simple:1.7.29")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}
