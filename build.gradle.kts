plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.45.1.0")
    implementation("org.slf4j:slf4j-nop:2.0.9")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }

    // Build a fat JAR: bundle the SQLite JDBC driver's classes directly
    // into the output JAR so `java -jar` works standalone, with no
    // classpath setup required by the end user.
    from({
        configurations.runtimeClasspath.get().filter { it.exists() }.map {
            if (it.isDirectory) it else zipTree(it)
        }
    }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}
