package com.pusat.absenta.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pusat.absenta.ui.theme.AbsentaTheme

data class AttendanceRecord(
    val id: Int,
    val date: String,
    val checkInTime: String,
    val checkOutTime: String,
    val status: String,
    val locationIn: String,
    val locationOut: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    // Dummy Data
    val historyList = listOf(
        AttendanceRecord(1, "25 April 2026", "07:50", "17:05", "Hadir", "Kantor Pusat", "Kantor Pusat"),
        AttendanceRecord(2, "24 April 2026", "08:15", "17:00", "Terlambat", "Kantor Pusat", "Kantor Pusat"),
        AttendanceRecord(3, "23 April 2026", "-", "-", "Sakit", "-", "-"),
        AttendanceRecord(4, "22 April 2026", "07:45", "17:10", "Hadir", "Kantor Pusat", "Kantor Pusat"),
        AttendanceRecord(5, "21 April 2026", "08:00", "17:00", "Hadir", "Kantor Pusat", "Kantor Pusat")
    )

    var selectedRecord by remember { mutableStateOf<AttendanceRecord?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    // Filter States
    var selectedMonth by remember { mutableStateOf("April 2026") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Riwayat Absensi", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Filter Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bulan: $selectedMonth",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Button(
                    onClick = { /* TODO: Show Date Picker / Dropdown */ },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Filter", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Filter")
                }
            }

            // List Riwayat
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historyList) { record ->
                    HistoryCard(
                        record = record,
                        onClick = {
                            selectedRecord = record
                            showBottomSheet = true
                        }
                    )
                }
            }
        }

        // Bottom Sheet untuk Detail
        if (showBottomSheet && selectedRecord != null) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
            ) {
                DetailBottomSheetContent(record = selectedRecord!!)
            }
        }
    }
}

@Composable
fun HistoryCard(record: AttendanceRecord, onClick: () -> Unit) {
    val statusColor = when (record.status) {
        "Hadir" -> MaterialTheme.colorScheme.primary
        "Terlambat" -> Color(0xFFFFA000) // Orange
        "Sakit" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = record.date,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Masuk: ${record.checkInTime} | Pulang: ${record.checkOutTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Status Badge
            Surface(
                color = statusColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
            ) {
                Text(
                    text = record.status,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun DetailBottomSheetContent(record: AttendanceRecord) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = "Detail Absensi",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        DetailRow(label = "Tanggal", value = record.date)
        DetailRow(label = "Status", value = record.status)
        Divider(modifier = Modifier.padding(vertical = 12.dp))
        
        Text("Waktu", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        DetailRow(label = "Jam Masuk", value = record.checkInTime)
        DetailRow(label = "Jam Pulang", value = record.checkOutTime)
        
        Divider(modifier = Modifier.padding(vertical = 12.dp))
        
        Text("Lokasi", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        DetailRowWithIcon(icon = Icons.Filled.LocationOn, label = "Lokasi Masuk", value = record.locationIn)
        Spacer(modifier = Modifier.height(8.dp))
        DetailRowWithIcon(icon = Icons.Filled.LocationOn, label = "Lokasi Pulang", value = record.locationOut)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DetailRowWithIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    AbsentaTheme {
        HistoryScreen()
    }
}
