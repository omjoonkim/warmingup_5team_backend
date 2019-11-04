package com.depromeet.where_cats.configuration

import com.depromeet.where_cats.db.DummyDb
import com.depromeet.where_cats.db.DummyDbSpec
import com.depromeet.where_cats.db.InMemoryDb
import com.depromeet.where_cats.db.InMemoryDbSpec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DIConfiguration {

    @Bean
    fun dummyDb(): DummyDbSpec = DummyDb()

    @Bean
    fun inMemoryDb(): InMemoryDbSpec = InMemoryDb()
}