package com.bada.west.domain

import com.bada.west.domain.entity.BaseEntity
import jakarta.persistence.*

data class Comment(
    @Id
    val id: String? = null,

    val groupId: String,

    val userId: String,

    val username: String,

    var body: String,


    ) : BaseEntity()