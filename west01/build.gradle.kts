dependencies {


    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.7.2")
    implementation("io.micrometer:context-propagation:1.0.3")

    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    //implementation group: 'jakarta.persistence', name: 'jakarta.persistence-api', version: '3.1.0'
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")


    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("at.favre.lib:bcrypt:0.9.0")

    //runtimeOnly("io.r2dbc:r2dbc-h2")
    implementation("io.asyncer:r2dbc-mysql:1.0.2") // R2DBC MySQL Driver
    // My Sql
    runtimeOnly("com.mysql:mysql-connector-j")

    //testImplementation("com.h2database:h2")
}