package com.example.tommocassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tommocassignment.data.Vehicle
import com.example.tommocassignment.data.VehicleDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class VehicleViewModel(private val dao: VehicleDao) : ViewModel() {

    val vehicles = dao.getAllVehicles().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val totalVehicles = vehicles.map { it.size }
    val totalEV = vehicles.map { list -> list.count { it.fuelType == "Electric" } }

    fun saveVehicle(vehicle: Vehicle) {
        viewModelScope.launch { dao.insertVehicle(vehicle) }
    }

    // Age Calculation Logic
    fun getVehicleAge(yearString: String): String {
        val purchaseYear = yearString.toIntOrNull() ?: return ""
        //Present Year
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // 1-12

        val purchaseMonth = 1

        var diffYears = currentYear - purchaseYear
        var diffMonths = currentMonth - purchaseMonth

        if (diffMonths < 0) {
            diffYears--
            diffMonths += 12
        }

        return when {
            diffYears > 0 -> "$diffYears years $diffMonths months"
            else -> "$diffMonths months"
        }
    }
}