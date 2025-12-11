package com.a_abdoun.spacexlaunchesapp.ui.company

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a_abdoun.spacexlaunchesapp.data.model.CompanyInfo

@Composable
fun CompanyScreen(viewModel: CompanyViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is CompanyUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CompanyUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Fehler beim Laden der Firmendaten")
            }
        }

        is CompanyUiState.Success -> {
            val company = (state as CompanyUiState.Success).company
            CompanyContent(company)
        }
    }
}

@Composable
fun CompanyContent(company: CompanyInfo) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "SpaceX - ${company.name}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Gründer: ${company.founder} (${company.founded})")
        Text("CEO: ${company.ceo}")
        Text("COO: ${company.coo}")
        Text("CTO: ${company.cto}")
        Text("CTO für Antriebssysteme: ${company.cto_propulsion}")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Mitarbeiterzahl: ${company.employees}")
        Text("Startplätze: ${company.launch_sites}")
        Text("Testgelände: ${company.test_sites}")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Firmensitz:",
            fontWeight = FontWeight.SemiBold
        )
        Text("${company.headquarters.address}, ${company.headquarters.city}, ${company.headquarters.state}")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Unternehmensübersicht:",
            fontWeight = FontWeight.SemiBold
        )
        Text(company.summary)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Webseite: ${company.links.website}")
        company.links.twitter?.let { Text("Twitter: $it") }
        company.links.elon_twitter?.let { Text("Elon Musks Twitter: $it") }
    }
}
