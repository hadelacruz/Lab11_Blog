package com.example.lab11

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home-screen", "Save Data", Icons.Filled.AddCircle)
    object Publications : Screen("publications-screen", "Publications", Icons.Filled.CheckCircle)
    object Profile : Screen("profile-screen", "Show Data", Icons.Filled.Person)
}