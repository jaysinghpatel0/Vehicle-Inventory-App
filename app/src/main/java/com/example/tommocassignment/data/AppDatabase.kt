package com.example.tommocassignment.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}