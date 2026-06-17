package com.example.avaliaws_08.ui.state

import com.example.avaliaws_08.ui.model.Competidor

sealed class ResultadoAvaliacao {
    data class aprovado(val competidor: Competidor, val notaFinal: Int) : ResultadoAvaliacao()
    data class reprovado(val competidor: Competidor, val notaFinal: Int, val motivo: String) : ResultadoAvaliacao()
    data class pendente(val competidor: Competidor, val pendencias: String) : ResultadoAvaliacao()
}