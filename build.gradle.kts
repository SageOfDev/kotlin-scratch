plugins {
    kotlin("jvm") version "2.0.10"
}

group = "com.nexon.fastdata.fastjoin"
version = "1.0-SNAPSHOT"

val coroutineVersion = "1.7.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

    // kafka
    implementation("org.apache.kafka:kafka-clients:3.7.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}