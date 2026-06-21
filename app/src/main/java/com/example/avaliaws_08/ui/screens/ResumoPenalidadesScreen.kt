package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.model.Penalidade
import com.example.avaliaws_08.repository.AppRepository

private data class ResumoPorCompetidor(
    val competidor: Competidor,
    val penalidades: List<Penalidade>,
    val totalPerdido: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumoPenalidadesScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumo de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        val avaliados = AppRepository.avaliarTodos()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (avaliados.isEmpty()) {
                Text(
                    text = "Resumo indisponível. Nenhuma avaliação executada.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                val resumos = avaliados.map { (competidor, par) ->
                    val (penalidades, _) = par
                    ResumoPorCompetidor(
                        competidor = competidor,
                        penalidades = penalidades,
                        totalPerdido = penalidades.sumOf { it.pontos }
                    )
                }

                val todasPenalidades = resumos.flatMap { it.penalidades }
                val penalidadeMaisFrequente = todasPenalidades
                    .groupingBy { it.motivo }
                    .eachCount()
                    .maxByOrNull { it.value }
                    ?.key

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Penalidade mais frequente",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = penalidadeMaisFrequente ?: "Nenhuma penalidade registrada")
                            }
                        }
                    }

                    items(resumos) { resumo ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = resumo.competidor.nomeCompleto,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = "-${resumo.totalPerdido} pts")
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                if (resumo.penalidades.isEmpty()) {
                                    Text(
                                        text = "Nenhuma penalidade aplicada.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } else {
                                    resumo.penalidades.forEach { penalidade ->
                                        Text(
                                            text = "• ${penalidade.motivo} (-${penalidade.pontos} pts)",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
