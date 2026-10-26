package com.example.service.ui.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(authVm: AuthViewModel) {
    val context = LocalContext.current

    // Проверяем авторизацию при первом запуске экрана
    LaunchedEffect(Unit) {
        authVm.checkAuth(context)
    }

    if (authVm.isLoggedIn) {
        // === ЭКРАН ЛИЧНОГО КАБИНЕТА ===
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("Личный кабинет", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))

            val user = authVm.currentUser
            if (user != null) {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A38))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Логин: ${user.username}", color = Color.White)
                        Text("Имя: ${user.first_name}", color = Color.White)
                        Text("Фамилия: ${user.last_name}", color = Color.White)
                        Text("Активен: ${if (user.is_active) "Да" else "Нет"}", color = Color.White)
                    }
                }
            } else {
                CircularProgressIndicator(color = Color(0xFF5288C1))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { authVm.logout(context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("Выйти")
            }
        }
    } else {
        // === ЭКРАН ВХОДА ===
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Вход в систему", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            if (authVm.error != null) {
                Text(authVm.error!!, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { authVm.login(context, username, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !authVm.isLoading
            ) {
                if (authVm.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Войти")
                }
            }
        }
    }
}