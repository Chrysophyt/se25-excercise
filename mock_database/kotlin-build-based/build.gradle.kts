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

// Fetch feature selection from configuration
val featureRead = providers.gradleProperty("read").getOrElse("true").toBoolean()
val featureWrite = providers.gradleProperty("write").getOrElse("false").toBoolean()
val featureTransaction = providers.gradleProperty("transaction").getOrElse("false").toBoolean()
val featureLogging = providers.gradleProperty("logging").getOrElse("false").toBoolean()
val featureStorage = providers.gradleProperty("storageType").getOrElse("list")
project.logger.lifecycle("Storage: $featureStorage")

// The source set defines which files should be included during compilation.
// By including different files based on the selected features, we can create different variants.
sourceSets {
    main {
        kotlin {
            // This directory is always included; it contains the base code.
            setSrcDirs(listOf("src/main/kotlin/base"))

            // Add feature-specific code to source set based on feature selection
            when (featureStorage) {
                // Add specific directories to the source set
                "list" -> srcDir("src/main/kotlin/list")
                "map" -> srcDir("src/main/kotlin/map")
            }
            when {
                featureRead && !featureWrite && !featureTransaction && !featureLogging ->
                    srcDir("src/main/kotlin/read")
                !featureRead && featureWrite && !featureTransaction && !featureLogging ->
                    srcDir("src/main/kotlin/write")
                featureRead && featureWrite && !featureTransaction && !featureLogging ->
                    srcDir("src/main/kotlin/readwrite")
                featureRead && !featureWrite && !featureTransaction && featureLogging ->
                    srcDir("src/main/kotlin/readlog")
                !featureRead && featureWrite && !featureTransaction && featureLogging ->
                    srcDir("src/main/kotlin/writelog")
                !featureRead && featureWrite && featureTransaction && !featureLogging ->
                    srcDir("src/main/kotlin/writetxn")
                !featureRead && featureWrite && featureTransaction && featureLogging ->
                    srcDir("src/main/kotlin/writetxnlog")
                featureRead && featureWrite && !featureTransaction && featureLogging ->
                    srcDir("src/main/kotlin/readwritelog")
                featureRead && featureWrite && featureTransaction && !featureLogging ->
                    srcDir("src/main/kotlin/readwritetxn")
                featureRead && featureWrite && featureTransaction && featureLogging ->
                    srcDir("src/main/kotlin/readwritetxnlog")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
