// auth/TokenManager.kt
package com.example.service.auth

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token", null)
        set(value) = prefs.edit().putString("token", value).apply()

    fun clear() {
        prefs.edit().remove("token").apply()
    }
}