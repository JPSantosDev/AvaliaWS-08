package com.example.avaliaws_08.model

data class Pontuacao(

    val notaBase: Int,

    ){

    init {
        require(notaBase>=0 && notaBase<=100) {"Nota base não deve ser negativo e nem maior que 100"}
    }

}