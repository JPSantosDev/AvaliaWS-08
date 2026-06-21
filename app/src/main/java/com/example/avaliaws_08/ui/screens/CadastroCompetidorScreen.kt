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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.repository.AppRepository
import com.example.avaliaws_08.ui.validation.ValidacaoResultado
import com.example.avaliaws_08.ui.validation.ValidadorEstado
import com.example.avaliaws_08.ui.validation.ValidadorIdUnico
import com.example.avaliaws_08.ui.validation.ValidadorNomeCompetidor
import com.example.avaliaws_08.ui.validation.ValidadorUnidade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCompetidorScreen(navController: NavController) {

    var idTexto by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var unidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    var erroId by remember { mutableStateOf<String?>(null) }
    var erroNome by remember { mutableStateOf<String?>(null) }
    var erroUnidade by remember { mutableStateOf<String?>(null) }
    var erroEstado by remember { mutableStateOf<String?>(null) }

    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    fun validarTudo(): Boolean {
        val idInt = idTexto.toIntOrNull()

        val resultadoId = if (idInt == null) {
            ValidacaoResultado.Invalido("O identificador deve ser um número inteiro positivo.")
        } else {
            ValidadorIdUnico(AppRepository.competidores.map { it.id }).validar(idInt)
        }
        val resultadoNome = ValidadorNomeCompetidor().validar(nome)
        val resultadoUnidade = ValidadorUnidade().validar(unidade)
        val resultadoEstado = ValidadorEstado().validar(estado)

        erroId = (resultadoId as? ValidacaoResultado.Invalido)?.mensagem
        erroNome = (resultadoNome as? ValidacaoResultado.Invalido)?.mensagem
        erroUnidade = (resultadoUnidade as? ValidacaoResultado.Invalido)?.mensagem
        erroEstado = (resultadoEstado as? ValidacaoResultado.Invalido)?.mensagem

        return erroId == null && erroNome == null && erroUnidade == null && erroEstado == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro de Competidor") },
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
            OutlinedTextField(
                value = idTexto,
                onValueChange = {
                    idTexto = it
                    erroId = null
                    mensagemSucesso = null
                },
                label = { Text("Identificador") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = erroId != null,
                supportingText = { erroId?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    erroNome = null
                    mensagemSucesso = null
                },
                label = { Text("Nome completo") },
                isError = erroNome != null,
                supportingText = { erroNome?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = unidade,
                onValueChange = {
                    unidade = it
                    erroUnidade = null
                    mensagemSucesso = null
                },
                label = { Text("Unidade ou escola") },
                isError = erroUnidade != null,
                supportingText = { erroUnidade?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = estado,
                onValueChange = {
                    estado = it
                    erroEstado = null
                    mensagemSucesso = null
                },
                label = { Text("Estado ou regional") },
                isError = erroEstado != null,
                supportingText = { erroEstado?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (validarTudo()) {
                        val competidor = Competidor(
                            id = idTexto.toInt(),
                            nomeCompleto = nome.trim(),
                            unidade = unidade.trim(),
                            estado = estado.trim()
                        )
                        AppRepository.adicionarCompetidor(competidor)

                        mensagemSucesso = "Competidor cadastrado com sucesso."
                        idTexto = ""
                        nome = ""
                        unidade = ""
                        estado = ""
                    }
                }
            ) {
                Text("Cadastrar")
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
            }
        }
    }
}
