package com.example.tommocassignment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tommocassignment.R
import com.example.tommocassignment.data.Vehicle
import com.example.tommocassignment.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(viewModel: VehicleViewModel, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("Select a brand") }
    var model by remember { mutableStateOf("Select a model") }
    var fuelType by remember { mutableStateOf("Select fuel type") }
    var vehicleNumber by remember { mutableStateOf("") }
    var yearOfPurchase by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }

    var showBrandDialog by remember { mutableStateOf(false) }
    var showModelDialog by remember { mutableStateOf(false) }
    var showFuelDialog by remember { mutableStateOf(false) }

    val brandList = listOf("Tata", "Honda", "Hero", "Bajaj", "Yamaha", "Other")
    val modelList = listOf("Activa 4G", "Activa 5G", "Activa 6G", "Activa 125", "Activa 125 BS6", "Activa H-Smart", "Pulsar 150")
    val fuelList = listOf("Petrol", "Electric", "Diesel", "CNG")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Vehicle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("VEHICLE DETAILS", style = MaterialTheme.typography.labelMedium, color = Color.Gray)

            SelectionField("Brand", brand) { showBrandDialog = true }
            SelectionField("Model", model) { showModelDialog = true }
            SelectionField("Fuel Type", fuelType) { showFuelDialog = true }

            OutlinedTextField(
                value = vehicleNumber,
                onValueChange = { vehicleNumber = it },
                label = { Text("Vehicle Number") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))
            Text("OTHER DETAILS", style = MaterialTheme.typography.labelMedium, color = Color.Gray)

            OutlinedTextField(
                value = yearOfPurchase,
                onValueChange = { yearOfPurchase = it },
                label = { Text("Year of Purchase") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = ownerName,
                onValueChange = { ownerName = it },
                label = { Text("Owner Name") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.saveVehicle(Vehicle(brand = brand, model = model, fuelType = fuelType,
                        vehicleNumber = vehicleNumber, yearOfPurchase = yearOfPurchase, ownerName = ownerName))
                    onBack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Add Vehicle", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

    //Selection Dialog
    if (showBrandDialog) {
        SelectionDialog(
            title = "Select Vehicle Brand",
            options = brandList,
            selectedValue = brand,
            onSelect = { brand = it; showBrandDialog = false },
            onDismiss = { showBrandDialog = false }
        )
    }

    if (showModelDialog) {
        SelectionDialog(
            title = "Select Vehicle Model",
            options = modelList,
            selectedValue = model,
            onSelect = { model = it; showModelDialog = false },
            onDismiss = { showModelDialog = false }
        )
    }

    if (showFuelDialog) {
        SelectionDialog(
            title = "Select Fuel Type",
            options = fuelList,
            selectedValue = fuelType,
            onSelect = { fuelType = it; showFuelDialog = false },
            onDismiss = { showFuelDialog = false }
        )
    }
}

@Composable
fun SelectionField(label: String, value: String, onClick: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, null) },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray
        )
    )
}

@Composable
fun SelectionDialog(
    title: String,
    options: List<String>,
    selectedValue: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(options) { option ->
                    val isSelected = option == selectedValue
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFF2196F3) else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onSelect(option) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (title.contains("Brand")) {
                            BrandLogo(brandName = option)
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Text(text = option, modifier = Modifier.weight(1f), fontSize = 16.sp)
                        RadioButton(
                            selected = isSelected,
                            onClick = { onSelect(option) }
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF6200EE), fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
fun BrandLogo(brandName: String) {
    val imageRes = when (brandName) {
        "Tata" -> R.drawable.tata
        "Honda" -> R.drawable.honda
        "Hero" -> R.drawable.hero
        "Bajaj" -> R.drawable.bajaj
        "Yamaha" -> R.drawable.yamaha
        else -> R.drawable.ic_launcher_foreground
    }
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = brandName,
        modifier = Modifier.size(28.dp)
    )
}