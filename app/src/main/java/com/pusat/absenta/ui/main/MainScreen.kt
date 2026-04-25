package com.pusat.absenta.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.pusat.absenta.ui.attendance.AttendanceScreen
import com.pusat.absenta.ui.home.HomeScreen

@Composable
fun MainScreen() {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("Home", "Absensi", "Riwayat", "Profile")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.DateRange,
        Icons.Filled.List,
        Icons.Filled.Person
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Konten dari masing-masing tab
        when (selectedItem) {
            0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
            1 -> AttendanceScreen(modifier = Modifier.padding(innerPadding))
            2 -> Text("Halaman Riwayat", modifier = Modifier.padding(innerPadding))
            3 -> Text("Halaman Profile", modifier = Modifier.padding(innerPadding))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    com.pusat.absenta.ui.theme.AbsentaTheme {
        MainScreen()
    }
}
