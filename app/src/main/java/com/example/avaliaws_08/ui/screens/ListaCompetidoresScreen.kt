package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.navigation.Routes
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao

private fun situacaoDe(competidor: Competidor): String {
    val resultado = AppRepository.avaliar(competidor)?.second ?: return "Sem entrega"
    return when (resultado) {
        is ResultadoAvaliacao.Aprovado -> "Aprovado"
        is ResultadoAvaliacao.Reprovado -> "Reprovado"
        is ResultadoAvaliacao.Pendente -> "Pendente"
    }
}

private fun corDaSituacao(situacao: String): Color = when (situacao) {
    "Aprovado" -> Color(0xFF2E7D32)
    "Reprovado" -> Color(0xFFC62828)
    "Pendente" -> Color(0xFFF9A825)
    else -> Color(0xFF757575) // Sem entrega
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCompetidoresScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Competidores") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (AppRepository.competidores.isEmpty()) {
                Text(
                    text = "Nenhum competidor cadastrado ainda.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(AppRepository.competidores) { competidor ->
                        val situacao = situacaoDe(competidor)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (AppRepository.entregaDe(competidor.id) != null) {
                                        navController.navigate(Routes.resultadoRoute(competidor.id))
                                    } else {
                                        navController.navigate(Routes.entregaRoute(competidor.id))
                                    }
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = competidor.nomeCompleto,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = corDaSituacao(situacao),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = situacao,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }

                                Text(text = "ID: ${competidor.id}")
                                Text(text = "Unidade: ${competidor.unidade}")
                                Text(text = "Estado: ${competidor.estado}")
                            }
                        }
                    }
                }
            }
        }
    }
}
