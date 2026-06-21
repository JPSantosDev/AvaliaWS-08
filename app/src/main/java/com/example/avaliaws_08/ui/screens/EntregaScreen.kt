package com.example.avaliaws_08.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.navigation.Routes
import com.example.avaliaws_08.ui.validation.ValidacaoResultado
import com.example.avaliaws_08.ui.validation.ValidadorCommits
import com.example.avaliaws_08.ui.validation.ValidadorNotaBase
import com.example.avaliaws_08.ui.validation.ValidadorTempo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntregaScreen(navController: NavController, competidorId: Int) {

    val competidor = AppRepository.competidores.find { it.id == competidorId }

    var notaBaseTexto by remember { mutableStateOf("") }
    var commitsTexto by remember { mutableStateOf("") }
    var tempoTexto by remember { mutableStateOf("") }

    var readme by remember { mutableStateOf(false) }
    var checklist by remember { mutableStateOf(false) }
    var evidencias by remember { mutableStateOf(false) }
    var funcional by remember { mutableStateOf(false) }
    var apresentacao by remember { mutableStateOf(false) }
    var correcao by remember { mutableStateOf(false) }

    var erroNota by remember { mutableStateOf<String?>(null) }
    var erroCommits by remember { mutableStateOf<String?>(null) }
    var erroTempo by remember { mutableStateOf<String?>(null) }

    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    fun validarTudo(): Boolean {
        val nota = notaBaseTexto.toIntOrNull()
        val commits = commitsTexto.toIntOrNull()
        val tempo = tempoTexto.toIntOrNull()

        val resultadoNota = if (nota == null)
            ValidacaoResultado.Invalido("A nota base deve ser um número entre 0 e 100.")
        else ValidadorNotaBase().validar(nota)

        val resultadoCommits = if (commits == null)
            ValidacaoResultado.Invalido("A quantidade de commits deve ser um número válido.")
        else ValidadorCommits().validar(commits)

        val resultadoTempo = if (tempo == null)
            ValidacaoResultado.Invalido("O tempo de entrega deve ser um número válido maior que zero.")
        else ValidadorTempo().validar(tempo)

        erroNota = (resultadoNota as? ValidacaoResultado.Invalido)?.mensagem
        erroCommits = (resultadoCommits as? ValidacaoResultado.Invalido)?.mensagem
        erroTempo = (resultadoTempo as? ValidacaoResultado.Invalido)?.mensagem

        return erroNota == null && erroCommits == null && erroTempo == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entrega Técnica") },
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

            Text(
                text = competidor.nomeCompleto,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = "ID: ${competidor.id} • ${competidor.unidade}")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notaBaseTexto,
                onValueChange = { notaBaseTexto = it; erroNota = null; mensagemSucesso = null },
                label = { Text("Nota base (0 a 100)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = erroNota != null,
                supportingText = { erroNota?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = commitsTexto,
                onValueChange = { commitsTexto = it; erroCommits = null; mensagemSucesso = null },
                label = { Text("Quantidade de commits") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = erroCommits != null,
                supportingText = { erroCommits?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = tempoTexto,
                onValueChange = { tempoTexto = it; erroTempo = null; mensagemSucesso = null },
                label = { Text("Tempo de entrega (minutos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = erroTempo != null,
                supportingText = { erroTempo?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CheckboxLinha("Possui README técnico", readme) { readme = it }
            CheckboxLinha("Possui checklist de validação", checklist) { checklist = it }
            CheckboxLinha("Possui evidências de execução", evidencias) { evidencias = it }
            CheckboxLinha("Possui item funcional demonstrável", funcional) { funcional = it }
            CheckboxLinha("Realizou apresentação técnica", apresentacao) { apresentacao = it }
            CheckboxLinha("Corrigiu erro simples ao vivo", correcao) { correcao = it }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (validarTudo()) {
                        val entrega = Entrega(
                            notaBase = notaBaseTexto.toInt(),
                            quantCommit = commitsTexto.toInt(),
                            readme = readme,
                            evidencias = evidencias,
                            funcional = funcional,
                            apresentacao = apresentacao,
                            correcao = correcao,
                            tempo = tempoTexto.toInt(),
                            checklist = checklist
                        )
                        AppRepository.registrarEntrega(competidor.id, entrega)
                        mensagemSucesso = "Entrega técnica registrada com sucesso."
                    }
                }
            ) {
                Text("Registrar Entrega")
            }

            mensagemSucesso?.let { mensagem ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF5E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensagem,
                        modifier = Modifier.padding(12.dp),
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(Routes.resultadoRoute(competidor.id)) }
                ) {
                    Text("Ver Resultado")
                }
            }
        }
    }
}

@Composable
private fun CheckboxLinha(texto: String, marcado: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(checked = marcado, onCheckedChange = onChange)
        Text(text = texto)
    }
}
