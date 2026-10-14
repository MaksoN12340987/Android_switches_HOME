package com.example.service.ui.viewmodel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items

@Composable
fun FavoritesScreen(vm: SwitchesViewModel) {
    val favorites = vm.switches.filter { it.is_favorite }

    if (favorites.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "В избранном пока пусто",
                color = Color(0xFF6D7F8F),
                fontSize = 15.sp
            )
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(favorites, key = { it.id }) { device ->
                DeviceCard(
                    device = device,
                    onToggle = { turnOn -> vm.toggle(device, turnOn) },
                    onFavorite = { vm.toggleFavorite(device) }
                )
            }
        }
    }
}