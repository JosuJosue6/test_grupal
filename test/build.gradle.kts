plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.smallrye.reactive:mutiny:2.5.6")
    implementation("org.postgresql:r2dbc-postgresql:1.0.4.RELEASE")
}

tasks.test {
    useJUnitPlatform()
}