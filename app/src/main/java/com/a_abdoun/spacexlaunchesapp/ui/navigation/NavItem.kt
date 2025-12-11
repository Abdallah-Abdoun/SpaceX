package com.a_abdoun.spacexlaunchesapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.RocketLaunch


sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Launches : BottomNavItem("launches", "Launches", Icons.Filled.Star)
    object News : BottomNavItem("news", "News", Icons.Filled.Article)
    object Assets : BottomNavItem("assets", "Assets", Icons.Outlined.RocketLaunch)
    object Company : BottomNavItem("company", "Company", Icons.Filled.Business)
    object Settings : BottomNavItem("settings", "Settings", Icons.Filled.Settings)
}
