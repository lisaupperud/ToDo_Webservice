plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.lisaanna"
version = "0.0.1-SNAPSHOT"
description = "Webservice project - ToDo List"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("me.paulschwarz:spring-dotenv:4.0.0")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val buildFrontend by tasks.registering(Exec::class) {
    workingDir = file("frontend")
    commandLine = listOf("C:/Program Files/nodejs/npm.cmd", "run", "build")
    //commandLine = listOf("npm", "run", "build")
}

val copyFrontend by tasks.registering(Copy::class) {
    dependsOn(buildFrontend)
    from("frontend/dist")
    into("src/main/resources/static")
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(copyFrontend)
}
