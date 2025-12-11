package com.a_abdoun.spacexlaunchesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.a_abdoun.spacexlaunchesapp.data.network.RetrofitInstance
import com.a_abdoun.spacexlaunchesapp.data.repository.LaunchRepository
import com.a_abdoun.spacexlaunchesapp.ui.navigation.AppNavigation
import com.a_abdoun.spacexlaunchesapp.ui.launchlist.LaunchListViewModel
import com.a_abdoun.spacexlaunchesapp.ui.launchlist.LaunchListViewModelFactory
import com.a_abdoun.spacexlaunchesapp.ui.theme.SpaceXLaunchesAppTheme
import androidx.room.Room
import com.a_abdoun.spacexlaunchesapp.data.local.AppDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "spaceX_db"
        ).fallbackToDestructiveMigration(true)
            .build()

        val repository = LaunchRepository(
            apiService = RetrofitInstance.api,
            dao = db.launchDao(),
            context = applicationContext
        )



        setContent {
            SpaceXLaunchesAppTheme {
                val navController = rememberNavController()
                val viewModel: LaunchListViewModel = viewModel(
                    factory = LaunchListViewModelFactory(repository)
                )

                AppNavigation(navController = navController)

            }
        }
    }
}
