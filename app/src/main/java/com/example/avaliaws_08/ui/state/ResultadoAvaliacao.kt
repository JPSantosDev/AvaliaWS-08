package com.example.avaliaws_08.ui.state

import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.model.Pontuacao

sealed class ResultadoAvaliacao {
    data class Aprovado(val competidor: Competidor, val notaFinal: Pontuacao) : ResultadoAvaliacao()
    data class Reprovado(val competidor: Competidor, val notaFinal: Pontuacao, val motivo: String) : ResultadoAvaliacao()
    data class Pendente(val competidor: Competidor, val pendencias: String) : ResultadoAvaliacao()
}