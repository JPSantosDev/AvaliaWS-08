package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
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
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao

private data class LinhaRanking(
    val posicao: Int,
    val nome: String,
    val notaFinal: Int,
    val classificacao: String,
    val pontosPerdidos: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ranking") },
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
                    text = "Ranking indisponível. Nenhum competidor cadastrado.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                val semPosicao = avaliados.map { (competidor, par) ->
                    val (penalidades, resultado) = par
                    val pontosPerdidos = penalidades.sumOf { it.pontos }
                    val notaFinal = when (resultado) {
                        is ResultadoAvaliacao.Aprovado -> resultado.notaFinal.notaBase
                        is ResultadoAvaliacao.Reprovado -> resultado.notaFinal.notaBase
                        is ResultadoAvaliacao.Pendente -> 0
                    }
                    val classificacao = when (resultado) {
                        is ResultadoAvaliacao.Aprovado -> "Aprovado"
                        is ResultadoAvaliacao.Reprovado -> "Reprovado"
                        is ResultadoAvaliacao.Pendente -> "Pendente"
                    }
                    LinhaRanking(
                        posicao = 0,
                        nome = competidor.nomeCompleto,
                        notaFinal = notaFinal,
                        classificacao = classificacao,
                        pontosPerdidos = pontosPerdidos
                    )
                }

                val ordenados = semPosicao
                    .sortedWith(
                        compareByDescending<LinhaRanking> { it.notaFinal }
                            .thenBy { it.pontosPerdidos }
                            .thenBy { it.nome }
                    )
                    .mapIndexed { index, linha -> linha.copy(posicao = index + 1) }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
                            Text("#", modifier = Modifier.width(32.dp), fontWeight = FontWeight.Bold)
                            Text("Nome", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Text("Nota", modifier = Modifier.width(56.dp), fontWeight = FontWeight.Bold)
                            Text("Perdidos", modifier = Modifier.width(72.dp), fontWeight = FontWeight.Bold)
                        }
                    }
                    items(ordenados) { linha ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = "${linha.posicao}º",
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.width(32.dp)
                                        )
                                        Text(text = linha.nome)
                                    }
                                    Text(text = "${linha.notaFinal} pts")
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = linha.classificacao,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "-${linha.pontosPerdidos} pts perdidos",
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
