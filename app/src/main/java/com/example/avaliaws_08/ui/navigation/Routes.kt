package com.example.avaliaws_08.ui.navigation

object Routes {

    const val HOME = "home"

    const val CADASTRO_COMPETIDOR = "cadastro_competidor"

    const val LISTA_COMPETIDORES = "lista_competidores"

    const val ENTREGA = "entrega"
    const val ENTREGA_COM_ID = "entrega/{competidorId}"

    fun entregaRoute(competidorId: Int) = "entrega/$competidorId"

    const val RESULTADO = "resultado"
    const val RESULTADO_COM_ID = "resultado/{competidorId}"

    fun resultadoRoute(competidorId: Int) = "resultado/$competidorId"

    const val RANKING = "ranking"

    const val RESUMO_PENALIDADES = "resumo_penalidades"
}