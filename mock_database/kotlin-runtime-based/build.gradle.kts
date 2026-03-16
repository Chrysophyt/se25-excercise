plugins {
    kotlin("jvm") version "2.0.21"
}

group = "de.uni-saarland.cs.se.simpledb"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
