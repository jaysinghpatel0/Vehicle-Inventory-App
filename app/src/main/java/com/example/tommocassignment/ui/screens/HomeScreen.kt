package com.example.tommocassignment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tommocassignment.R
import com.example.tommocassignment.data.Vehicle
import com.example.tommocassignment.ui.components.FilterSheet
import com.example.tommocassignment.viewmodel.VehicleViewModel

@Composable
fun HomeScreen(viewModel: VehicleViewModel, onAddClick: () -> Unit) {
    val vehicleList by viewModel.vehicles.collectAsState()
    val totalCount by viewModel.totalVehicles.collectAsState(0)
    val evCount by viewModel.totalEV.collectAsState(0)

    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedBrand by remember { mutableStateOf<String?>(null) }
    var selectedFuel by remember { mutableStateOf<String?>(null) }

    val filteredList = vehicleList.filter {
        (selectedBrand == null || it.brand == selectedBrand) &&
                (selectedFuel == null || it.fuelType == selectedFuel)
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, null)
                Text(" Add Vehicle")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(Color.White)) {
            UserHeader()

            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Total Vehicles", totalCount.toString(), "🚗", Color(0xFFFFF3E0), Modifier.weight(1f))
                StatCard("Total EV", evCount.toString(), "⚡", Color(0xFFE8F5E9), Modifier.weight(1f))
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Vehicle Inventory List", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { showFilterSheet = true }, shape = RoundedCornerShape(8.dp)) {
                    Icon(painterResource(id = R.drawable.ic_filter), null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Filter", color = Color.Black)
                }
            }

            // Table with Borders
            Box(modifier = Modifier.padding(16.dp).border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)).clip(RoundedCornerShape(8.dp))) {
                VehicleTable(filteredList, viewModel)
            }
        }
    }

    if (showFilterSheet) {
        FilterSheet(
            onDismiss = { showFilterSheet = false },
            onApply = { brand, fuel ->
                selectedBrand = brand
                selectedFuel = fuel
                showFilterSheet = false
            }
        )
    }
}

@Composable
fun UserHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1B3E))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar",
            modifier = Modifier.size(48.dp).clip(CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Hi, Amin", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(" 👋", fontSize = 16.sp)
            }
            Text("Welcome back!", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun StatCard(label: String, count: String, symbol: String, bgColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = symbol, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = count, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun VehicleTable(vehicles: List<Vehicle>, viewModel: VehicleViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Table Header
        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F4F8)).height(56.dp), verticalAlignment = Alignment.CenterVertically) {
            TableCell("Model & Brand", Modifier.weight(1f), isHeader = true)
            TableDivider()
            TableCell("Vehicle Number", Modifier.weight(1.2f), isHeader = true)
            TableDivider()
            TableCell("Fuel Type", Modifier.weight(0.7f), isHeader = true)
            TableDivider()
            TableCell("Year of Purchase", Modifier.weight(1.2f), isHeader = true)
        }
        HorizontalDivider(color = Color(0xFFE0E0E0))

        LazyColumn {
            items(vehicles) { vehicle ->
                Row(modifier = Modifier.fillMaxWidth().height(64.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(vehicle.model, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(vehicle.brand, fontSize = 10.sp, color = Color.Gray)
                    }
                    TableDivider()
                    Text(vehicle.vehicleNumber, Modifier.weight(1.2f).padding(8.dp), color = Color(0xFF2196F3), fontSize = 12.sp)
                    TableDivider()
                    Text(vehicle.fuelType, Modifier.weight(0.7f).padding(8.dp), fontSize = 12.sp)
                    TableDivider()
                    Column(Modifier.weight(1.2f).padding(8.dp)) {
                        Text(vehicle.yearOfPurchase, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(viewModel.getVehicleAge(vehicle.yearOfPurchase), fontSize = 10.sp, color = Color.Gray)
                    }
                }
                HorizontalDivider(color = Color(0xFFE0E0E0))
            }
        }
    }
}

@Composable fun TableCell(text: String, modifier: Modifier, isHeader: Boolean = false) {
    Text(text, modifier.padding(8.dp), fontSize = 11.sp, fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal)
}

@Composable fun TableDivider() {
    VerticalDivider(modifier = Modifier.fillMaxHeight(), color = Color(0xFFE0E0E0), thickness = 1.dp)
}