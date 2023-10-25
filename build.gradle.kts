plugins {
    id("java")
    id("application")
}

application {
    mainClass.set("r01.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.tudalgo:fopbot:0.6.0")
}

tasks.test {
    useJUnitPlatform()
}