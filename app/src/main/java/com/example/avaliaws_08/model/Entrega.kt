package com.example.avaliaws_08.model

data class Entrega (
    val notaBase: Int,
    val quantCommit: Int,
    val readme: Boolean,
    val evidencias: Boolean,
    val funcional: Boolean,
    val apresentacao: Boolean,
    val correcao: Boolean,
    val tempo: Int,
    val checklist: Boolean,
){

    init{
        require(notaBase>=0 && notaBase<=100) {"Nota base não deve ser negativo e nem maior que 100"}
        require(quantCommit>=0) {"Quantidade de commits não deve ser negativa"}
        require(tempo>=0) {"Tempo de entrega não deve ser negativo"}
    }
}