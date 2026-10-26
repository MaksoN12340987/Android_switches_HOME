// api/dto/UserDto.kt
package com.example.service.api.dto

data class UserDto(
    val id: Int? = null,
    val username: String,
    val photo: String?,
    val first_name: String,
    val last_name: String,
    val email: String?,          // Добавьте, если есть в Django
    val phone_number: String?,          // Добавьте, если есть в Django (или в профиле)
    val is_superuser: Boolean,   // Суперюзер
    val is_active: Boolean,
    val groups: List<String>? = null // Группы (если бэкенд отдает строки, а не объекты)
)

// api/dto/LoginRequest.kt
data class LoginRequest(val username: String, val password: String)

// api/dto/LoginResponse.kt
data class LoginResponse(val token: String)