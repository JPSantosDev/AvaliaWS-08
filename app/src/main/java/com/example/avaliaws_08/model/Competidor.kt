package com.example.avaliaws_08.model

data class Competidor (
    val id: Int,
    val nomeCompleto: String,
    val unidade: String,
    val estado: String
){
    init {
        require(nomeCompleto.isNotBlank()) { "O nome completo do competidor não pode ser vazio." }
        require(unidade.isNotBlank()) { "A unidade não pode ser vazia." }
        require(estado.isNotBlank()) { "O estado não pode ser vazio." }
        require(id > 0) { "O ID do competidor deve ser um número positivo." }
    }
}