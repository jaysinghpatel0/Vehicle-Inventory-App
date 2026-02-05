package com.example.tommocassignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val model: String,
    val fuelType: String,
    val vehicleNumber: String,
    val yearOfPurchase: String,
    val ownerName: String
)
