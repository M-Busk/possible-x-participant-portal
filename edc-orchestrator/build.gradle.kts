
plugins {
	`java`
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "eu.possible-x"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.bootJar {
	mainClass.set("eu.possible_x.edc_orchestrator.EdcOrchestratorApplication")
	archiveBaseName.set("edc_orchestrator")
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.register<Copy>("copyWebApp") {
  description = "Copies the GUI into the resources of the Spring project."
  group = "Application"
  from("$rootDir/edc-gui/build/resources/browser")
  into(layout.buildDirectory.dir("resources/main/static/."))
}

tasks.named("compileJava") {
  dependsOn(":edc-gui:npmBuild")
}

tasks.named("processResources") {
  dependsOn("copyWebApp")

}