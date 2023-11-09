package com.bada.west.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


//@Configuration
//@EnableR2dbcRepositories
//class R2DBCConfig : AbstractR2dbcConfiguration() {
class R2DBCConfig  {

//    @Bean
//    fun init(connectionFactory: ConnectionFactory) =
//        ConnectionFactoryInitializer().apply {
//            setConnectionFactory(connectionFactory)
//            setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("scripts/schema.sql")))
//        }

//    override fun connectionFactory(): ConnectionFactory {
//
//        val connectionFactory = ConnectionFactories.get(
//                builder()
//                .option(DRIVER, "mysql")
//                .option(HOST, "chamdiary-db-01.cfvym5vcsxct.ap-northeast-2.rds.amazonaws.com:3306")
//                .option(USER, "chamdiary")
//                .option(PORT, 3306)
//                .option(PASSWORD, "start2023!!")
//                .option(DATABASE, "cham_202404")
//                .build())
//
//        //#    properties:
//        //#      lock_timeout: 40000
//        //#      statement_timeout: 40000
//        //#      idle_in_transaction_session_timeout: 40000
//        //#      connectTimeout: PT55S
////        val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
////            .maxIdleTime(Duration.ofSeconds(40000))
////            .maxCreateConnectionTime(Duration.ofSeconds(40000))
////            .maxLifeTime(Duration.ofMinutes(10))
////            .initialSize(30)
////            .maxSize(100)
////            .build()
//
//        val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
//            .maxIdleTime(Duration.ofMillis(1000))
//            .maxSize(20)
//            .build()
//
//        return ConnectionPool(configuration)
//    }
}