plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.assertj:assertj-core:3.24.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}