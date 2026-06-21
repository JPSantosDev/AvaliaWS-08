package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.navigation.Routes
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao

@Composable
fun HomeScreen(
    navController: NavController
) {
    val totalCompetidores = AppRepository.competidores.size
    val totalEntregas = AppRepository.entregas.size
    val resultados = AppRepository.avaliarTodos()

    val totalAvaliados = resultados.size
    val totalAprovados = resultados.count { it.second.second is ResultadoAvaliacao.Aprovado }
    val totalReprovados = resultados.count { it.second.second is ResultadoAvaliacao.Reprovado }
    val totalPendentes = resultados.count { it.second.second is ResultadoAvaliacao.Pendente } +
        (totalCompetidores - totalEntregas).coerceAtLeast(0)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text(
                text = "AvaliaWS 08",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Motor de Regras para Avaliação de Competidores",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (totalCompetidores == 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = "Nenhum competidor cadastrado ainda.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        ResumoLinha(rotulo = "Total de competidores", valor = totalCompetidores)
                    }
                    item {
                        ResumoLinha(rotulo = "Total de entregas", valor = totalEntregas)
                    }
                    item {
                        ResumoLinha(rotulo = "Total de avaliados", valor = totalAvaliados)
                    }
                    item {
                        ResumoLinha(rotulo = "Aprovados", valor = totalAprovados)
                    }
                    item {
                        ResumoLinha(rotulo = "Reprovados", valor = totalReprovados)
                    }
                    item {
                        ResumoLinha(rotulo = "Pendentes", valor = totalPendentes)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Routes.CADASTRO_COMPETIDOR) }
            ) {
                Text("Cadastrar Competidor")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { navController.navigate(Routes.LISTA_COMPETIDORES) }
            ) {
                Text("Lista de Competidores")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { navController.navigate(Routes.RANKING) }
            ) {
                Text("Ranking")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { navController.navigate(Routes.RESUMO_PENALIDADES) }
            ) {
                Text("Resumo de Penalidades")
            }
        }
    }
}

@Composable
private fun ResumoLinha(rotulo: String, valor: Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = rotulo)
            Text(text = valor.toString(), fontWeight = FontWeight.Bold)
        }
    }
}
