plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    // Fails with 2.10.1
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1")

    // Succeeds with 2.9.9
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.9")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
