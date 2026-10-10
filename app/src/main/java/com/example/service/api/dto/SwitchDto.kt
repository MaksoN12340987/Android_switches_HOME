package com.example.service.api.dto

data class SwitchDto(
    val id: Int,
    val name: String,
    val location: String,
    val on_off: Boolean,
    val image: String?
)
