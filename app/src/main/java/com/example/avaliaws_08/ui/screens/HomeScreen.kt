package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.ui.navigation.Routes

@Composable
fun HomeScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "AvaliaWS 08",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            onClick = {
                navController.navigate(Routes.CADASTRO_COMPETIDOR)
            }
        ) {
            Text("Cadastrar Competidor")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                navController.navigate(Routes.LISTA_COMPETIDORES)
            }
        ) {
            Text("Lista de Competidores")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                navController.navigate(Routes.RANKING)
            }
        ) {
            Text("Ranking")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                navController.navigate(Routes.RESUMO_PENALIDADES)
            }
        ) {
            Text("Resumo de Penalidades")
        }
    }
}