package com.example.avaliaws_08.service
import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.model.Penalidade
import com.example.avaliaws_08.model.Pontuacao
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao

class ClassificadorResultado (
    private val penalidades: List<Penalidade>,
    private val competidor: Competidor,
    private val calculadora: CalculadoraNota

){

    fun classificar(): ResultadoAvaliacao{
        val temCritica = penalidades.any{it.critica}
        val temPendencia = penalidades.any{it.pendencia}
        val notaFinal = calculadora.calcular()

        if(temCritica){
            return ResultadoAvaliacao.Reprovado(
                competidor,notaFinal,"Sem item funcional")

        }
        else if (temPendencia){
            return ResultadoAvaliacao.Pendente(
                competidor,"Sem correção ao vivo"
            )
        }
        else if(notaFinal.notaBase<70){
            return ResultadoAvaliacao.Reprovado(
                competidor,notaFinal, "Nota abaixo de 70"
            )
        }
        else{
            return ResultadoAvaliacao.Aprovado(
                competidor,notaFinal
            )
        }
    }
}
