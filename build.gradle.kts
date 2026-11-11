plugins {
    kotlin("jvm") version "2.2.21"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.codeborne:selenide:7.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    systemProperty("selenide.headless", System.getProperty("selenide.headless", "false"))
}
