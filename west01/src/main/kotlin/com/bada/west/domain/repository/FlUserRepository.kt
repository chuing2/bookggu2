package com.bada.west.domain.repository

import com.bada.west.domain.entity.FlUserAto
import com.bada.west.domain.entity.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FlUserRepository : CoroutineCrudRepository<FlUserAto, String> {

    suspend fun findBySsEeZ04(email: String): FlUserAto?
}