package com.example.tommocassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(onDismiss: () -> Unit, onApply: (String?, String?) -> Unit) {
    var selectedCategory by remember { mutableStateOf("Brand") }
    var tempBrand by remember { mutableStateOf<String?>(null) }
    var tempFuel by remember { mutableStateOf<String?>(null) }

    val brands = listOf("Tata", "Honda", "Hero", "Bajaj", "Yamaha")
    val fuels = listOf("Petrol", "Electric", "Diesel", "CNG")

    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = Color.White) {
        Column(modifier = Modifier.fillMaxHeight(0.6f)) {
            // Header
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Filter", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onDismiss) { Text("✕") }
            }

            Row(modifier = Modifier.weight(1f)) {
                // Left Tabs
                Column(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFF7F8FA))) {
                    FilterTab("Brand", selectedCategory == "Brand") { selectedCategory = "Brand" }
                    FilterTab("Fuel Type", selectedCategory == "Fuel Type") { selectedCategory = "Fuel Type" }
                }
                // Right Options
                LazyColumn(Modifier.weight(2f).padding(16.dp)) {
                    val list = if (selectedCategory == "Brand") brands else fuels
                    items(list) { item ->
                        Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(item, Modifier.weight(1f))
                            RadioButton(
                                selected = if (selectedCategory == "Brand") tempBrand == item else tempFuel == item,
                                onClick = { if (selectedCategory == "Brand") tempBrand = item else tempFuel = item }
                            )
                        }
                    }
                }
            }

            // Bottom Buttons
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        tempBrand = null
                        tempFuel = null
                    } // Clear All Logic
                ) { Text("Clear all", color = Color.Black) }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    onClick = { onApply(tempBrand, tempFuel) }
                ) { Text("Apply") }
            }
        }
    }
}

@Composable
fun FilterTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().clickable { onClick() }.background(if (isSelected) Color.White else Color.Transparent).padding(16.dp)) {
        Text(label, color = if (isSelected) Color(0xFF2196F3) else Color.Black, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}