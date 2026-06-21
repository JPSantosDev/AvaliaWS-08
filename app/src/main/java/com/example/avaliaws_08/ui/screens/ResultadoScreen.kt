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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadoScreen(navController: NavController, competidorId: Int) {

    val competidor = AppRepository.competidores.find { it.id == competidorId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultado Individual") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            if (competidor == null) {
                Text("Competidor não encontrado.")
                return@Scaffold
            }

            val entrega = AppRepository.entregaDe(competidor.id)
            val resultadoAvaliacao = AppRepository.avaliar(competidor)

            if (entrega == null || resultadoAvaliacao == null) {
                Text("Este competidor ainda não possui entrega registrada.")
                return@Scaffold
            }

            val (penalidades, resultado) = resultadoAvaliacao
            val totalPerdido = penalidades.sumOf { it.pontos }

            Text(
                text = competidor.nomeCompleto,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = "ID: ${competidor.id} • ${competidor.unidade} • ${competidor.estado}")

            Spacer(modifier = Modifier.height(16.dp))

            LinhaResultado("Nota base", entrega.notaBase.toString())
            LinhaResultado("Total de pontos perdidos", totalPerdido.toString())

            val notaFinal = when (resultado) {
                is ResultadoAvaliacao.Aprovado -> resultado.notaFinal.notaBase
                is ResultadoAvaliacao.Reprovado -> resultado.notaFinal.notaBase
                is ResultadoAvaliacao.Pendente -> entrega.notaBase - totalPerdido
            }
            LinhaResultado("Nota final", notaFinal.toString())

            val classificacao = when (resultado) {
                is ResultadoAvaliacao.Aprovado -> "Aprovado"
                is ResultadoAvaliacao.Reprovado -> "Reprovado"
                is ResultadoAvaliacao.Pendente -> "Pendente"
            }
            LinhaResultado("Classificação final", classificacao)

            when (resultado) {
                is ResultadoAvaliacao.Reprovado -> LinhaResultado("Motivo", resultado.motivo)
                is ResultadoAvaliacao.Pendente -> LinhaResultado("Pendências", resultado.pendencias)
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Penalidades aplicadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (penalidades.isEmpty()) {
                Text("Nenhuma penalidade aplicada.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(penalidades) { penalidade ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = penalidade.motivo)
                                Text(
                                    text = "-${penalidade.pontos} pts",
                                    color = Color(0xFFC62828),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LinhaResultado(rotulo: String, valor: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = rotulo)
            Text(text = valor, fontWeight = FontWeight.Bold)
        }
    }
}
