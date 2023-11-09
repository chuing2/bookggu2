package com.bada.west.model

import com.bada.west.domain.Comment

data class CommentRequest(
    val body: String,
)

data class CommentResponse(
    val id: String,
    val groupId: String,
    val userId: String,
    val body: String,
    val username: String? = null
)

fun Comment.toResponse() = CommentResponse(
    id = id!!,
    groupId = groupId!!,
    userId = userId,
    body = body,
    username = username,
)