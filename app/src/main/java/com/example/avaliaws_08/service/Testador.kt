package com.example.avaliaws_08.service

import android.util.Log
import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.model.Pontuacao

class Testador {

    fun testarDominio() {

        val competidor = Competidor(
            id = 1,
            nomeCompleto = "Jean Pierre",
            unidade = "SENAI",
            estado = "PE"
        )

        val entrega = Entrega(
            notaBase = 100,
            quantCommit = 5,
            readme = true,
            evidencias = true,
            funcional = true,
            apresentacao = true,
            correcao = true,
            tempo = 120,
            checklist = true
        )

        val motor = MotorDeRegras(
            listOf(
                SemReadMe(),
                SemEvidencias(),
                MenosCommits(),
                SemChecklist(),
                SemItemFuncional(),
                SemApresentacao(),
                TempoLongo(),
                SemCorrecao()
            )
        )

        val penalidades = motor.avaliar(entrega)

        Log.d("TESTE", "Penalidades: $penalidades")

        val calculadora = CalculadoraNota(
            Pontuacao(entrega.notaBase),
            penalidades
        )

        val classificador = ClassificadorResultado(
            penalidades,
            competidor,
            calculadora
        )

        val resultado = classificador.classificar()

        Log.d("TESTE", "Resultado: $resultado")
    }
}