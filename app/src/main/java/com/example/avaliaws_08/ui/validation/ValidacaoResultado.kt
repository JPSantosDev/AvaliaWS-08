package com.example.avaliaws_08.ui.validation

sealed class ValidacaoResultado {
    object Valido : ValidacaoResultado()
    data class Invalido(val mensagem: String) : ValidacaoResultado()
}

interface Validador<T> {
    fun validar(valor: T): ValidacaoResultado
}

class ValidadorNomeCompetidor: Validador<String>{
    override fun validar(valor: String): ValidacaoResultado {
        val nome = valor.trim()
        return when{
            nome.isBlank() -> ValidacaoResultado.Invalido("O nome do competidor não pode ser vazio.")
            nome.length < 3 -> ValidacaoResultado.Invalido("O nome do competidor deve conter pelo menos 3 caracteres.")
            !nome.contains(" ") -> ValidacaoResultado.Invalido("O nome do competidor deve conter pelo menos um sobrenome.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorEstado: Validador<String>{
    override fun validar(valor: String): ValidacaoResultado {
        val estado = valor.trim()
        return when {
            estado.isBlank() -> ValidacaoResultado.Invalido("O estado do competidor não pode ser vazio.")
            estado.length < 2 || estado.length > 3 -> ValidacaoResultado.Invalido("O estado do competidor deve conter  2 caracteres.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorUnidade: Validador<String>{
    override fun validar(valor: String): ValidacaoResultado {
        val unidade = valor.trim()
        return when {
            unidade.isBlank() -> ValidacaoResultado.Invalido("A unidade do competidor não pode ser vazia.")
            unidade.length < 2 -> ValidacaoResultado.Invalido("A unidade do competidor deve conter pelo menos 2 caracteres.")
            else -> ValidacaoResultado.Valido
        }
    }
}
class ValidadorId: Validador<Int>{
    override fun validar(valor: Int): ValidacaoResultado {
        return when {
            valor <= 0 -> ValidacaoResultado.Invalido("O ID do competidor deve ser um número positivo.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorIdUnico(private val idsExistentes: List<Int>): Validador<Int>{
    override fun validar(valor: Int): ValidacaoResultado {
        return when {
            valor <= 0 -> ValidacaoResultado.Invalido("O ID do competidor deve ser um número positivo.")
            idsExistentes.contains(valor) -> ValidacaoResultado.Invalido("Já existe um competidor cadastrado com este identificador.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorNotaBase: Validador<Int>{
    override fun validar(valor: Int): ValidacaoResultado {
        return when {
            valor < 0 || valor > 100 -> ValidacaoResultado.Invalido("A nota base deve estar entre 0 e 100.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorCommits: Validador<Int>{
    override fun validar(valor: Int): ValidacaoResultado {
        return when {
            valor < 0 -> ValidacaoResultado.Invalido("A quantidade de commits não pode ser negativa.")
            else -> ValidacaoResultado.Valido
        }
    }
}

class ValidadorTempo: Validador<Int>{
    override fun validar(valor: Int): ValidacaoResultado {
        return when {
            valor <= 0 -> ValidacaoResultado.Invalido("O tempo de entrega deve ser maior que zero.")
            else -> ValidacaoResultado.Valido
        }
    }
}