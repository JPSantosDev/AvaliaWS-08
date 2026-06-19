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
)