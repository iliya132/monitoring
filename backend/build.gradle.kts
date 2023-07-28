import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.hypersistence:hypersistence-utils-hibernate-60")
    implementation("org.postgresql:postgresql")
    implementation("org.influxdb:influxdb-java")
    implementation("org.liquibase:liquibase-core")
    testImplementation("org.springframework:spring-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:influxdb")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("monitoring-app")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.iliya132.MonitoringBackendApp"))
        }
    }

    build {
        dependsOn("shadowJar")
    }
}
