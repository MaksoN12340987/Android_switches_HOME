package com.example.service

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.service.ui.theme.SERVICETheme
import com.example.service.ui.viewmodel.SwitchesScreen
import com.example.service.ui.viewmodel.SwitchesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SERVICETheme {
                SERVICEApp()
            }
        }
    }
}

@Preview
@Composable
fun SERVICEApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    val switchesVm: SwitchesViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                modifier = Modifier.height(70.dp),
                containerColor = Color(0xCC1A1D24),
                tonalElevation = 0.dp
            ) {
                AppDestinations.entries.forEach { dest ->
                    NavigationBarItem(
                        selected = dest == currentDestination,
                        onClick = { currentDestination = dest },
                        icon = {
                            Icon(
                                painter = painterResource(dest.icon),
                                contentDescription = dest.label,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = {
                            Text(text = dest.label, fontSize = 11.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF22C55E),
                            selectedTextColor = Color(0xFF22C55E),
                            indicatorColor = Color(0x3322C55E),
                            unselectedIconColor = Color(0xFF9AA0A6),
                            unselectedTextColor = Color(0xFF9AA0A6)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val switchesVm: SwitchesViewModel = viewModel()
            when (currentDestination) {
                AppDestinations.HOME -> SwitchesScreen(vm = switchesVm)
                AppDestinations.FAVORITES -> Greeting("Информация")
                AppDestinations.PROFILE -> Greeting("Профиль")
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Главная", R.drawable.ic_home),
    FAVORITES("Информация", R.drawable.ic_favorite),
    PROFILE("Профиль", R.drawable.ic_account_box),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}