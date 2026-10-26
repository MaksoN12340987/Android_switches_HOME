// api/dto/UserDto.kt
package com.example.service.api.dto

data class UserDto(
    val id: Int? = null,
    val username: String,
    val first_name: String,
    val last_name: String,
    val is_active: Boolean,
    val groups: List<Any>? = null // Пока просто заглушка, если группы не нужны
)

// api/dto/LoginRequest.kt
data class LoginRequest(val username: String, val password: String)

// api/dto/LoginResponse.kt
data class LoginResponse(val token: String)