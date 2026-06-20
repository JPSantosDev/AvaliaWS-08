package com.example.avaliaws_08.service

import com.example.avaliaws_08.model.Penalidade
import com.example.avaliaws_08.model.Pontuacao

class CalculadoraNota(
    private val nota: Pontuacao,
    private val penalidades: List<Penalidade>
) {

    fun calcular(): Pontuacao{
        val desconto = penalidades.sumOf { it.pontos }
        return Pontuacao((nota.notaBase-desconto).coerceAtLeast(0))
    }
}