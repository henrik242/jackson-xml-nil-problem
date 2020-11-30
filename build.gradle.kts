plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

//val jacksonVersion = "2.11.3"  // succeeds
val jacksonVersion = "2.12.0"  //fails

val junitVersion = "5.5.1"

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
