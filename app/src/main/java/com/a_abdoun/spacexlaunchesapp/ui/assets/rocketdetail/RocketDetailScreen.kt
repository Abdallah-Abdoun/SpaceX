package com.a_abdoun.spacexlaunchesapp.ui.assets.rocketdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.a_abdoun.spacexlaunchesapp.data.network.SpaceXApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailScreen(
    rocketId: String,
    onBack: () -> Unit
) {
    val viewModel: RocketDetailViewModel = viewModel(
        factory = RocketDetailViewModelFactory(SpaceXApiService.create(), rocketId)
    )
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is RocketDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RocketDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Fehler beim Laden")
            }
        }

        is RocketDetailUiState.Success -> {
            val rocket = (state as RocketDetailUiState.Success).rocket

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(rocket.name) },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    rocket.flickr_images.firstOrNull()?.let { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    Text("📌 Type: ${rocket.type}", style = MaterialTheme.typography.bodyMedium)
                    Text("✅ Active: ${if (rocket.active) "Yes" else "No"}")
                    Text("🎯 Success Rate: ${rocket.success_rate_pct}%")
                    Text("💸 Cost per Launch: $${rocket.cost_per_launch}")
                    Text("🛫 First Flight: ${rocket.first_flight}")
                    Text("📍 Country: ${rocket.country}")
                    Text("🏢 Company: ${rocket.company}")
                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text("📏 Dimensions", style = MaterialTheme.typography.titleMedium)
                    Text("Height: ${rocket.height.meters} m / ${rocket.height.feet} ft")
                    Text("Diameter: ${rocket.diameter.meters} m / ${rocket.diameter.feet} ft")
                    Text("Mass: ${rocket.mass.kg} kg / ${rocket.mass.lb} lb")

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("📦 Payload Weights", style = MaterialTheme.typography.titleMedium)
                    rocket.payload_weights.forEach {
                        Text("- ${it.name}: ${it.kg} kg / ${it.lb} lb")
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text("🧪 First Stage", style = MaterialTheme.typography.titleMedium)
                    Text("Engines: ${rocket.first_stage.engines}")
                    Text("Reusable: ${rocket.first_stage.reusable}")
                    Text("Fuel: ${rocket.first_stage.fuel_amount_tons} tons")
                    Text("Burn Time: ${rocket.first_stage.burn_time_sec} sec")
                    Text("Thrust (Sea): ${rocket.first_stage.thrust_sea_level.kN} kN")
                    Text("Thrust (Vacuum): ${rocket.first_stage.thrust_vacuum.kN} kN")

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("🧪 Second Stage", style = MaterialTheme.typography.titleMedium)
                    Text("Engines: ${rocket.second_stage.engines}")
                    Text("Reusable: ${rocket.second_stage.reusable}")
                    Text("Fuel: ${rocket.second_stage.fuel_amount_tons} tons")
                    Text("Burn Time: ${rocket.second_stage.burn_time_sec} sec")
                    Text("Thrust: ${rocket.second_stage.thrust.kN} kN")
                    Text("Payload Fairing Height: ${rocket.second_stage.payloads.composite_fairing.height.meters} m")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text("⚙️ Engines", style = MaterialTheme.typography.titleMedium)
                    Text("Type: ${rocket.engines.type}")
                    Text("Version: ${rocket.engines.version}")
                    Text("Layout: ${rocket.engines.layout}")
                    Text("Number: ${rocket.engines.number}")
                    Text("Propellant 1: ${rocket.engines.propellant_1}")
                    Text("Propellant 2: ${rocket.engines.propellant_2}")
                    Text("Thrust (Sea): ${rocket.engines.thrust_sea_level.kN} kN")
                    Text("Thrust (Vacuum): ${rocket.engines.thrust_vacuum.kN} kN")
                    Text("ISP (Sea Level): ${rocket.engines.isp.sea_level}")
                    Text("ISP (Vacuum): ${rocket.engines.isp.vacuum}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text("📄 Description", style = MaterialTheme.typography.titleMedium)
                    Text(rocket.description)
                }
            }
        }
    }
}
