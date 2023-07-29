import com.google.protobuf.gradle.*

plugins {
    id("idea")
    id("org.springframework.boot") version "3.0.5"
    id("com.google.protobuf") version "0.9.4"
}
val errorProneAnnotations: String by project

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
    implementation("io.grpc:grpc-netty")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("com.google.protobuf:protobuf-java")
    implementation("com.google.errorprone:error_prone_annotations")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.56.1"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}
