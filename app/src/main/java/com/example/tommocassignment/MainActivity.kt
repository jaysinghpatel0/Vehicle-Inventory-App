package com.example.tommocassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.tommocassignment.data.AppDatabase
import com.example.tommocassignment.ui.screens.AddVehicleScreen
import com.example.tommocassignment.ui.screens.HomeScreen
import com.example.tommocassignment.viewmodel.VehicleViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1: Properly initialize the Room Database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vehicle_inventory"
        ).build()

        //2: Initialize ViewModel
        val viewModel = VehicleViewModel(db.vehicleDao())

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onAddClick = { navController.navigate("add_vehicle") }
                        )
                    }
                    composable("add_vehicle") {
                        AddVehicleScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}