// viewmodel/AuthViewModel.kt
package com.example.service.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service.api.ApiClient
import com.example.service.api.dto.LoginRequest
import com.example.service.auth.TokenManager
import kotlinx.coroutines.launch
import com.example.service.api.dto.UserDto

class AuthViewModel : ViewModel() {
    // Состояния
    var isLoggedIn by mutableStateOf(false)
        private set
    var currentUser by mutableStateOf<UserDto?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun checkAuth(context: Context) {
        val token = TokenManager(context).token
        if (token != null) {
            isLoggedIn = true
            fetchUserProfile()
        }
    }

    fun login(context: Context, username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = ApiClient.api.login(LoginRequest(username, password))
                TokenManager(context).token = response.token
                isLoggedIn = true
                fetchUserProfile()
            } catch (e: Exception) {
                error = "Ошибка входа: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun logout(context: Context) {
        TokenManager(context).clear()
        isLoggedIn = false
        currentUser = null
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                // Если у вас есть эндпоинт /api/users/me/, используйте его.
                // Если нет — берем первого из списка (для теста)
                val users = ApiClient.api.getUsers()
                currentUser = users.firstOrNull()
            } catch (e: Exception) {
                error = "Ошибка загрузки профиля"
            }
        }
    }
}