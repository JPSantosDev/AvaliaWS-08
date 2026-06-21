package com.example.avaliaws_08.repository

import com.example.avaliaws_08.model.Competidor
import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.model.Penalidade
import com.example.avaliaws_08.model.Pontuacao
import com.example.avaliaws_08.service.CalculadoraNota
import com.example.avaliaws_08.service.ClassificadorResultado
import com.example.avaliaws_08.service.MenosCommits
import com.example.avaliaws_08.service.MotorDeRegras
import com.example.avaliaws_08.service.SemApresentacao
import com.example.avaliaws_08.service.SemChecklist
import com.example.avaliaws_08.service.SemCorrecao
import com.example.avaliaws_08.service.SemEvidencias
import com.example.avaliaws_08.service.SemItemFuncional
import com.example.avaliaws_08.service.SemReadMe
import com.example.avaliaws_08.service.TempoLongo
import com.example.avaliaws_08.ui.state.ResultadoAvaliacao
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf

/**
 * Repositório simples em memória (object singleton).
 *
 * Mantém os dados compartilhados entre as telas via NavHost, sem usar
 * ViewModel, banco de dados ou persistência, conforme o briefing da semana.
 *
 * Usa mutableStateListOf para que o Compose recomponha automaticamente
 * quando a lista de competidores ou entregas for alterada.
 */
object AppRepository {

    // Lista de competidores cadastrados
    val competidores = mutableStateListOf<Competidor>()

    // Entregas registradas, indexadas pelo id do competidor
    val entregas = mutableStateMapOf<Int, Entrega>()

    // Motor de regras com as 8 regras concretas implementadas no service
    private fun criarMotor(): MotorDeRegras = MotorDeRegras(
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

    fun existeId(id: Int): Boolean = competidores.any { it.id == id }

    fun adicionarCompetidor(competidor: Competidor) {
        competidores.add(competidor)
    }

    fun registrarEntrega(competidorId: Int, entrega: Entrega) {
        entregas[competidorId] = entrega
    }

    fun entregaDe(competidorId: Int): Entrega? = entregas[competidorId]

    /**
     * Aplica o motor de regras sobre a entrega de um competidor e
     * retorna a lista de penalidades junto com o resultado classificado.
     * Retorna null se o competidor ainda não possui entrega (pendente sem avaliação).
     */
    fun avaliar(competidor: Competidor): Pair<List<Penalidade>, ResultadoAvaliacao>? {
        val entrega = entregas[competidor.id] ?: return null

        val penalidades = criarMotor().avaliar(entrega)
        val calculadora = CalculadoraNota(Pontuacao(entrega.notaBase), penalidades)
        val classificador = ClassificadorResultado(penalidades, competidor, calculadora)

        return penalidades to classificador.classificar()
    }

    /** Retorna o resultado de todos os competidores que já possuem entrega registrada. */
    fun avaliarTodos(): List<Pair<Competidor, Pair<List<Penalidade>, ResultadoAvaliacao>>> {
        return competidores.mapNotNull { competidor ->
            avaliar(competidor)?.let { resultado -> competidor to resultado }
        }
    }
}
