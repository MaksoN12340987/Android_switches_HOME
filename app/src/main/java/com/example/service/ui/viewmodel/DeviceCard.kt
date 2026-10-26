package com.example.service.ui.viewmodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.service.api.dto.SwitchDto
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.service.R

@Composable
fun DeviceCard(
    device: SwitchDto,
    onToggle: (Boolean) -> Unit,
    onFavorite: () -> Unit
) {
    Column {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFF0f1115).copy(alpha = 0.7f),
            border = BorderStroke(
                width = 0.5.dp,
                color = Color(0xFF0d6efd).copy(alpha = 0.4f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* опционально: открыть детали */ }
                    .height(76.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ─── Аватар ────────────────────────────────
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2B5278)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = device.image,  // <-- Передаем строку прямо как есть!
                        contentDescription = "Изображение",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        // Показываем иконку-заглушку, если фото нет или оно грузится
                        error = painterResource(R.drawable.ic_device_placeholder), // или Icons.Default.Person
                        placeholder = painterResource(R.drawable.ic_device_placeholder)
                    )
                }

                Spacer(Modifier.width(12.dp))

                // ─── Имя + статус ─────────────────────────
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Text(
                        text = if (device.on_off) "Включено" else "Отключено",
                        color = if (device.on_off)
                            Color(0xFF4DCD5E)
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }


                // ─── Сердечко ─────────────────────────────
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (device.is_favorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "В избранное",
                        tint = if (device.is_favorite) Color(0xFFE53935) else Color(0xFF6D7F8F)
                    )
                }

                Spacer(Modifier.width(4.dp))

                // ─── Кнопки ──────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompactButton(
                        text = "Вкл",
                        accent = Color(0xFF20c997),
                        active = device.on_off,
                        onClick = { onToggle(true) }
                    )
                    CompactButton(
                        text = "Выкл",
                        accent = Color(0xFFfd7e14),
                        active = !device.on_off,
                        onClick = { onToggle(false) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CompactButton(
    text: String,
    accent: Color,
    active: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = accent.copy(alpha = if (active) 0.25f else 0.3f),
            contentColor = if (active) accent else accent.copy(alpha = 0.9f)
        ),
        modifier = Modifier.defaultMinSize(minWidth = 0.dp, minHeight = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}