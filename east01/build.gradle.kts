apply(plugin= "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    //runtimeOnly("io.r2dbc:r2dbc-h2")

    //runtimeOnly("com.mysql:mysql-connector-j")

//    implementation("io.asyncer:r2dbc-mysql:1.0.2") // R2DBC MySQL Driver
//        implementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
}