plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.infovars"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}