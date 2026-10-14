package com.example.service

import android.app.Application
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
import com.example.service.ui.theme.SERVICETheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.util.DebugLogger
import com.example.service.ui.viewmodel.FavoritesScreen
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


class Loggers : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .logger(DebugLogger())
            .build()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SERVICEApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val switchesVm: SwitchesViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {

        // ─── ФОН ─────────────────────────────────────
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop   // ← растянуть/обрезать по экрану
        )

        // Затемнение поверх картинки (чтобы текст был читаем)
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF0f1115).copy(alpha = 0.06f))   // ← полупрозрачный тёмный слой
//        )

        // ─── КОНТЕНТ ────────────────────────────────
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = { /* поиск */ }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Поиск",
                                tint = Color(0xFF5288C1)
                            )
                        }
                    },
                    title = {
                        Text(
                            when (currentDestination) {
                                AppDestinations.HOME -> "Наш дом"
                                AppDestinations.FAVORITES -> "Информация"
                                AppDestinations.PROFILE -> "Профиль"
                            },
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Black
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = { NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 40.dp,
                    modifier = Modifier.height(110.dp)
                ) {
                    AppDestinations.entries.forEach { dest ->
                        NavigationBarItem(
                            selected = dest == currentDestination,
                            onClick = { currentDestination = dest },
                            icon = {
                                Icon(
                                    painter = painterResource(dest.icon),
                                    contentDescription = dest.label,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(dest.label, fontSize = 14.sp)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF5288C1),
                                selectedTextColor = Color(0xFF5288C1),
                                indicatorColor = Color.Transparent,   // ← убираем серый «пилюльный» фон
                                unselectedIconColor = Color(0xFF6D7F8F),
                                unselectedTextColor = Color(0xFF6D7F8F)
                            )
                        )
                    }
            } }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                when (currentDestination) {
                    AppDestinations.HOME -> SwitchesScreen(vm = switchesVm)
                    AppDestinations.FAVORITES -> FavoritesScreen(vm = switchesVm)
                    AppDestinations.PROFILE -> Placeholder("Профиль")
                }
            }
        }

//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            containerColor = Color.Transparent,
//            topBar = {
//                TopAppBar(
//                    actions = {
//                        IconButton(onClick = { /* поиск */ }) {
//                            Icon(
//                                Icons.Default.Search,
//                                contentDescription = "Поиск",
//                                tint = Color(0xFF5288C1)
//                            )
//                        }
//                    },
//                    title = {
//                        Text(
//                            when (currentDestination) {
//                                AppDestinations.HOME -> "Наш дом"
//                                AppDestinations.FAVORITES -> "Информация"
//                                AppDestinations.PROFILE -> "Профиль"
//                            },
//                            fontSize = 40.sp,
//                            fontWeight = FontWeight.Black
//                        )
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = Color.Transparent,
//                        titleContentColor = Color.White
//                    )
//                )
//            },
//            bottomBar = {
//                NavigationBar(
//                    containerColor = Color(0xFF9aa0a6).copy(alpha = 0.2f),
//                    tonalElevation = 40.dp,
//                    modifier = Modifier.height(110.dp)
//                ) {
//                    AppDestinations.entries.forEach { dest ->
//                        NavigationBarItem(
//                            selected = dest == currentDestination,
//                            onClick = { currentDestination = dest },
//                            icon = {
//                                Icon(
//                                    painter = painterResource(dest.icon),
//                                    contentDescription = dest.label,
//                                    modifier = Modifier.size(22.dp)
//                                )
//                            },
//                            label = {
//                                Text(dest.label, fontSize = 14.sp)
//                            },
//                            colors = NavigationBarItemDefaults.colors(
//                                selectedIconColor = Color(0xFF5288C1),
//                                selectedTextColor = Color(0xFF5288C1),
//                                indicatorColor = Color.Transparent,   // ← убираем серый «пилюльный» фон
//                                unselectedIconColor = Color(0xFF6D7F8F),
//                                unselectedTextColor = Color(0xFF6D7F8F)
//                            )
//                        )
//                    }
//                }
//            }
//        ) { innerPadding ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//            ) {
//                when (currentDestination) {
//                    AppDestinations.HOME -> SwitchesScreen()
//                    AppDestinations.FAVORITES -> Placeholder("Избранное")
//                    AppDestinations.PROFILE -> Placeholder("Профиль")
//                }
//            }
//        }
    }
}

@Composable
fun Placeholder(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text, color = MaterialTheme.colorScheme.onBackground)
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Главная", R.drawable.ic_home),
    FAVORITES("Избранное", R.drawable.ic_favorite),
    PROFILE("Профиль", R.drawable.ic_account_box),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}