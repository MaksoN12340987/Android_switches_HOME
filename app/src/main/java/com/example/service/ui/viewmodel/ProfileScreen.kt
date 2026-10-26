package com.example.service.ui.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.service.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(authVm: AuthViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authVm.checkAuth(context)
    }

    if (authVm.isLoggedIn) {
        ProfileContent(authVm, context)
    } else {
        LoginContent(authVm, context)
    }
}

// ==========================================
// ЭКРАН ПРОФИЛЯ (Когда пользователь вошел)
// ==========================================
@Composable
fun ProfileContent(authVm: AuthViewModel, context: android.content.Context) {
    val user = authVm.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (user != null) {
            // ─── БЛОК 1: Аватар, Имя, Фамилия ───
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A38)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Аватар (Заглушка)
                    Box(
                        modifier = Modifier
                            .size(260.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2B5278)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = user.photo,  // <-- Передаем строку прямо как есть!
                            contentDescription = "Аватар",
                            modifier = Modifier
                                .size(260.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            // Показываем иконку-заглушку, если фото нет или оно грузится
                            error = painterResource(R.drawable.ic_device_placeholder), // или Icons.Default.Person
                            placeholder = painterResource(R.drawable.ic_device_placeholder)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "${user.first_name} ${user.last_name}".trim().ifEmpty { "Без имени" },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (user.is_active) "В сети" else "Неактивен",
                            fontSize = 14.sp,
                            color = if (user.is_active) Color(0xFF4DCD5E) else Color(0xFF6D7F8F)
                        )
                    }
                }
            }

            // ─── БЛОК 2: Контакты (Логин, Почта, Телефон) ───
            Text(
                "Личные данные",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A38)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileInfoRow("Логин", user.username)
                    HorizontalDivider(color = Color(0xFF101921), modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoRow("Почта", user.email ?: "Не указана")
                    HorizontalDivider(color = Color(0xFF101921), modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoRow("Телефон", user.phone_number ?: "Не указан")
                }
            }

            // ─── БЛОК 3: Права и Группы ───
            Text(
                "Сервисная информация",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A38)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileInfoRow(
                        "Статус",
                        if (user.is_superuser) "Суперпользователь" else "Обычный пользователь",
                        valueColor = if (user.is_superuser) Color(0xFFE53935) else Color.White
                    )

                    if (!user.groups.isNullOrEmpty()) {
                        HorizontalDivider(color = Color(0xFF101921), modifier = Modifier.padding(vertical = 8.dp))
                        Text("Группы:", color = Color(0xFF6D7F8F), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))

                        // Выводим группы списком через запятую
                        // (Если приходят объекты, используйте user.groups.joinToString { it.name })
                        Text(
                            text = user.groups.joinToString(", "),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }

        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF5288C1))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка выхода
        Button(
            onClick = { authVm.logout(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
        ) {
            Text("Выйти из аккаунта")
        }
    }
}

// Вспомогательный компонент для строки "Ключ: Значение"
@Composable
fun ProfileInfoRow(label: String, value: String, valueColor: Color = Color.White) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color(0xFF6D7F8F), fontSize = 15.sp)
        Text(value, color = valueColor, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

// ==========================================
// ЭКРАН ВХОДА (Без изменений, для полноты)
// ==========================================
@Composable
fun LoginContent(authVm: AuthViewModel, context: android.content.Context) {
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
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
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