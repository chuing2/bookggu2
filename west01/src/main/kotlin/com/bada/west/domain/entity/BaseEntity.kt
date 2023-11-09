package com.bada.west.domain.entity


import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
//import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
//import javax.persistence.EntityListeners
//import javax.persistence.MappedSuperclass
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(

    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
    )