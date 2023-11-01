plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

// Fails with `java.lang.AssertionError: Expected "Simple" but actual was null` in jackson 2.12.3 and above (including 2.16.0-rc1)
// Works fine in 2.12.2 and earlier
val jacksonVersion = "2.16.0-rc1"
//val jacksonVersion = "2.12.2"

val junitVersion = "5.5.1"

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}