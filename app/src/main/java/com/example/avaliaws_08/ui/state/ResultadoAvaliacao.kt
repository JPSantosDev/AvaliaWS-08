package com.example.avaliaws_08.ui.state

import com.example.avaliaws_08.model.Competidor

sealed class ResultadoAvaliacao {
    data class Aprovado(val competidor: Competidor, val notaFinal: Int) : ResultadoAvaliacao()
    data class Reprovado(val competidor: Competidor, val notaFinal: Int, val motivo: String) : ResultadoAvaliacao()
    data class Pendente(val competidor: Competidor, val pendencias: String) : ResultadoAvaliacao()
}