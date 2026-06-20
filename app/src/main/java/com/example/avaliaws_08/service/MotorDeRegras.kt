package com.example.avaliaws_08.service

import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.model.Penalidade

class MotorDeRegras(
    private val regras: List<RegraAvaliacao>
) {
    fun avaliar(entrega: Entrega): List<Penalidade> {

        return regras.mapNotNull { regras ->
            regras.avaliar(entrega)
        }
    }
}