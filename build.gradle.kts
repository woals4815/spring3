plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.0.12")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("org.springframework:spring-jdbc:6.0.12")
    implementation("org.springframework:spring-context-support:6.0.12")
    implementation("javax.mail:javax.mail-api:1.6.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework:spring-test:6.0.12")
}

tasks.test {
    useJUnitPlatform()
}