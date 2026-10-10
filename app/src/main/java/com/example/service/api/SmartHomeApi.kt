package com.example.service.api

import com.example.service.api.dto.SwitchDto
import com.example.service.api.dto.SwitchPatch
import retrofit2.http.*

interface SmartHomeApi {

    @GET("api/")
    suspend fun getSwitches(): List<SwitchDto>

    @PATCH("api/on_off/{id}/")
    suspend fun setState(
        @Path("id") id: Int,
        @Body body: SwitchPatch
    ): SwitchDto
}