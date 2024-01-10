@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    java
    application
}

application {
    mainClass.set("r10.Main")
}

dependencies {
    implementation(libs.annotations)
    implementation(libs.fopbot)
    implementation(libs.algoutils.student)
    testImplementation(libs.junit.core)
}

tasks {
    val runDir = File("build/run")
    withType<JavaExec> {
        doFirst {
            runDir.mkdirs()
        }
        workingDir = runDir
    }
    test {
        doFirst {
            runDir.mkdirs()
        }
        workingDir = runDir
        useJUnitPlatform()
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}