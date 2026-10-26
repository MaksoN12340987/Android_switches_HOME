package com.example.service.api

import com.example.service.api.dto.FavoritePatch
import com.example.service.api.dto.LoginRequest
import com.example.service.api.dto.LoginResponse
import com.example.service.api.dto.SwitchDto
import com.example.service.api.dto.SwitchPatch
import com.example.service.api.dto.UserDto
import retrofit2.http.*

interface SmartHomeApi {

    @GET("api/")
    suspend fun getSwitches(): List<SwitchDto>

    @PATCH("api/on_off/{id}/")
    suspend fun setState(
        @Path("id") id: Int,
        @Body body: SwitchPatch
    ): SwitchDto

    @PATCH("api/Add_favorites/{id}/")
    suspend fun setFavorite(
        @Path("id") id: Int,
        @Body body: FavoritePatch
    ): SwitchDto

    @POST("api/api-token-auth/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/users/") // Или ваш эндпоинт для текущего пользователя
    suspend fun getUsers(): List<UserDto>
}
