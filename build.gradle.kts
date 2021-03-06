import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

group = "tv.itsash"
version = "1.1"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.21")

    //JDA no audio
    implementation("net.dv8tion:JDA:4.3.0_304") {
        exclude(null, "opus-java")
    }

    //Logging
    implementation("ch.qos.logback:logback-classic:1.2.6")

    //Database
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.3")

    //Config
    implementation("ch.jalu:configme:1.3.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }
}

application {
    mainClass.set("MainKt")
}