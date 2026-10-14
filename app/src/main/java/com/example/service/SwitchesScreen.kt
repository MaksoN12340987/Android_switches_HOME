package com.example.service

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import com.example.service.ui.viewmodel.DeviceCard
import com.example.service.ui.viewmodel.SwitchesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchesScreen(vm: SwitchesViewModel) {
    when {
        vm.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF5288C1))
        }
        vm.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Ошибка: ${vm.error}", color = Color(0xFFE53935))
        }
        else -> LazyColumn(
            modifier = Modifier.fillMaxSize(),       // ← без padding
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(vm.switches, key = { it.id }) { device ->
                DeviceCard(
                    device = device,
                    onToggle = { turnOn -> vm.toggle(device, turnOn) },
                    onFavorite = { vm.toggleFavorite(device) }
                )
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun com.example.service.SwitchesScreen(vm: SwitchesViewModel = viewModel()) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Наш дом",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        color = Color(0xFFFFFFFF)
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF17212B),
//                    titleContentColor = Color(0xFFFFFFFF)
//                )
//            )
//        },
//        containerColor = Color(0xFF17212B)
//    ) { padding ->
//        when {
//            vm.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
//                CircularProgressIndicator(color = Color(0xFF5288C1))
//            }
//            vm.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
//                Text("Ошибка: ${vm.error}", color = Color(0xFFE53935))
//            }
//            else -> LazyColumn(
//                modifier = Modifier.padding(padding).fillMaxSize()
//            ) {
//                items(vm.switches, key = { it.id }) { device ->
//                    DeviceCard(
//                        device = device,
//                        onToggle = { turnOn -> vm.toggle(device, turnOn) }
//                    )
//                }
//            }
//        }
//    }
//}