# 🚗 Vehicle Inventory App

A professional Android application built to manage and track vehicle inventory, developed as part of the **Project**. The app follows modern Android development practices, ensuring scalability, maintainability, and a clean user experience.

---

## 📸 App Screenshots

| Home Screen | Add Vehicle | Filter Options | Brand Selection |
| :---: | :---: | :---: | :---: |
| <img src="Home Page.jpg" width="200"> | <img src="Add Vehicle Page.jpg" width="200"> | <img src="Filter View.jpg" width="200"> | <img src="Selection Page.jpg" width="200"> |

> **Note:** Above screenshots demonstrate the UI layout, 4-column table, and custom selection dialogs with logos.

---

## 🚀 Key Features

* **Dashboard Overview**: View total vehicle count and specific EV (Electric Vehicle) statistics with visual symbols (🚗 & ⚡).
* **Vehicle Inventory List**: A detailed grid-based table displaying Model, Brand, Vehicle Number, Fuel Type, and Year of Purchase.
* **Dynamic Age Calculation**: Automatically calculates the vehicle's age (e.g., "2 years 7 months") based on the current date.
* **Advanced Filtering**: Filter inventory by Brand and Fuel Type using a custom tabbed bottom sheet.
* **Persistent Storage**: Uses a local database (Room) to ensure data is saved and available offline.
* **Custom Selection Dialogs**: User-friendly brand selection featuring official logos (Tata, Honda, Hero, etc.).

---

## 🛠 Tech Stack & Architecture

This project strictly adheres to the **Clean Architecture** principles and **MVVM** design pattern.

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose (Declarative UI)
* **Database**: Room Persistence Library
* **Reactive Streams**: Kotlin Flow and StateFlow for real-time UI updates
* **Asynchronous Programming**: Coroutines for non-blocking database operations

---

## 📁 Project Structure

```text
com.example.tommocassignment
├── data            # Room Database, Entity (Vehicle), and DAO
├── ui
│   ├── components  # Reusable UI components like FilterSheet
│   ├── screens     # Main UI Screens (Home, AddVehicle)
│   └── theme       # Typography, Color, and Theme definitions
├── viewmodel       # VehicleViewModel for state management
└── MainActivity.kt # Navigation and App Entry point
