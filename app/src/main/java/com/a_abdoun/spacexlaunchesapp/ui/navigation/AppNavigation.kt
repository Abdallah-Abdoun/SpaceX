package com.a_abdoun.spacexlaunchesapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import android.net.Uri
import com.a_abdoun.spacexlaunchesapp.ui.components.WebViewScreen
import androidx.navigation.compose.composable
import androidx.room.Room
import com.a_abdoun.spacexlaunchesapp.data.local.AppDatabase
import com.a_abdoun.spacexlaunchesapp.data.network.SpaceXApiService
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository
import com.a_abdoun.spacexlaunchesapp.ui.assets.AssetsScreen
import com.a_abdoun.spacexlaunchesapp.ui.assets.rocketdetail.RocketDetailScreen
import com.a_abdoun.spacexlaunchesapp.ui.company.CompanyScreen
import com.a_abdoun.spacexlaunchesapp.ui.launchdetail.LaunchDetailScreen
import com.a_abdoun.spacexlaunchesapp.ui.launchdetail.LaunchDetailViewModel
import com.a_abdoun.spacexlaunchesapp.ui.launchdetail.LaunchDetailViewModelFactory
import com.a_abdoun.spacexlaunchesapp.ui.launchlist.LaunchListScreen
import com.a_abdoun.spacexlaunchesapp.ui.launchlist.LaunchListViewModel
import com.a_abdoun.spacexlaunchesapp.ui.launchlist.LaunchListViewModelFactory
import com.a_abdoun.spacexlaunchesapp.ui.news.NewsScreen
import com.a_abdoun.spacexlaunchesapp.ui.settings.SettingsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Launches,
        BottomNavItem.News,
        BottomNavItem.Assets,
        BottomNavItem.Company,
        BottomNavItem.Settings
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Launches.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Launches.route) {
                val context = LocalContext.current
                val db = remember {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "spaceX_db"
                    ).fallbackToDestructiveMigration(true).build()
                }
                val dao = remember { db.launchDao() }
                val repository = remember {
                    LaunchRepository(
                        apiService = SpaceXApiService.create(),
                        dao = dao,
                        context = context.applicationContext
                    )
                }
                val viewModel: LaunchListViewModel = viewModel(
                    factory = LaunchListViewModelFactory(repository)
                )
                LaunchListScreen(viewModel) { launch ->
                    navController.navigate("launchDetail/${launch.id}")
                }
            }


            composable("launchDetail/{launchId}") { backStackEntry ->
                val context = LocalContext.current

                val db = remember {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "spaceX_db"
                    ).fallbackToDestructiveMigration(true).build()
                }

                val dao = remember { db.launchDao() }

                val repository = remember {
                    LaunchRepository(
                        apiService = SpaceXApiService.create(),
                        dao = dao,
                        context = context.applicationContext
                    )
                }

                val launchId = backStackEntry.arguments?.getString("launchId") ?: ""

                val factory = remember(launchId) {
                    LaunchDetailViewModelFactory(repository, launchId)
                }

                val detailViewModel: LaunchDetailViewModel = viewModel(factory = factory)

                LaunchDetailScreen(
                    viewModel = detailViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("rocketDetail/{rocketId}") { backStackEntry ->
                val rocketId = backStackEntry.arguments?.getString("rocketId") ?: ""
                RocketDetailScreen(
                    rocketId = rocketId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(BottomNavItem.News.route) {
                NewsScreen(navController)
            }

            composable(BottomNavItem.Assets.route) {
                AssetsScreen(navController)
            }

            composable(BottomNavItem.Company.route) {
                CompanyScreen()
            }

            composable(BottomNavItem.Settings.route) {
                SettingsScreen()
            }
            composable("webview/{url}") { backStackEntry ->
                val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
                val decodedUrl = Uri.decode(encodedUrl)

                WebViewScreen(
                    url = decodedUrl,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
