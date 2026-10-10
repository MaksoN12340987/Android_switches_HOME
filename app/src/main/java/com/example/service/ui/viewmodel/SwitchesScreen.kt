package com.example.service.ui.viewmodel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchesScreen(vm: SwitchesViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Наш дом") })
        }
    ) { padding ->
        when {
            vm.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
            vm.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text("Ошибка: ${vm.error}")
            }
            else -> LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(vm.switches, key = { it.id }) { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { turnOn -> vm.toggle(device, turnOn) }
                    )
                }
            }
        }
    }
}