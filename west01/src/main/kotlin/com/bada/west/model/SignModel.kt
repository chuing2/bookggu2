package com.bada.west .model

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val team: String,
)

data class SignInRequest(
    val email: String,
    val password: String,
)

data class SignInResponse(
    val email: String,
    val username: String,
    val token: String,
)