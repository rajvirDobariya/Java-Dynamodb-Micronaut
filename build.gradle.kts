plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.2.0"
    id("io.micronaut.aot") version "4.2.0"
}

version = "0.1"
group = "com.techaot"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client")

    // https://mvnrepository.com/artifact/io.micronaut.aws/micronaut-aws-sdk-v2
    implementation("io.micronaut.aws:micronaut-aws-sdk-v2:4.2.1")
    // https://mvnrepository.com/artifact/com.github.ksuid/ksuid
    implementation("com.github.ksuid:ksuid:1.1.2")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("software.amazon.awssdk:dynamodb-enhanced")

    runtimeOnly("org.yaml:snakeyaml")
}


application {
    mainClass.set("com.techaot.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("20")
    targetCompatibility = JavaVersion.toVersion("20")
}


graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.techaot.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}



