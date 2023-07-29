import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    id("fr.brouillard.oss.gradle.jgitver")
    id("io.spring.dependency-management")
    id("org.springframework.boot") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(17)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {
    group = "ru.iliya132"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val testcontainersBom: String by project
    val lombok: String by project
    val hypersistence: String by project
    val protobufBom: String by project
    val grpc: String by project

    val jmh: String by project
    val asm: String by project

    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBom")
                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
            }
            dependency("org.projectlombok:lombok:$lombok")
            dependency("io.grpc:grpc-netty:$grpc")

            dependency("org.openjdk.jmh:jmh-core:$jmh")
            dependency("org.openjdk.jmh:jmh-generator-annprocess:$jmh")
            dependency("org.ow2.asm:asm-commons:$asm")
            dependency("io.grpc:grpc-protobuf:$grpc")
            dependency("io.grpc:grpc-stub:$grpc")
        }
        configurations.all {
            resolutionStrategy {
                failOnVersionConflict()
                force("org.jetbrains:annotations:17.0.0") // influxdb uses 14.0
                force("io.hypersistence:hypersistence-utils-hibernate-60:${hypersistence}")
                force("javax.xml.bind:jaxb-api:2.3.1") // hypersistence
                force("org.checkerframework:checker-qual:3.5.0") // hypersistence
                force("com.google.errorprone:error_prone_annotations:2.18.0")
            }
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing", "-Werror"))
    }

    plugins.apply(fr.brouillard.oss.gradle.plugins.JGitverPlugin::class.java)
    extensions.configure<fr.brouillard.oss.gradle.plugins.JGitverPluginExtension> {
        strategy("PATTERN")
        nonQualifierBranches("main,master")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}
