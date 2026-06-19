package com.example.avaliaws_08.service

import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.model.Penalidade

interface RegraAvaliacao {
    fun avaliar(entrega: Entrega): Penalidade?
}

class SemReadMe: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.readme)
            Penalidade("Sem ReadME",5,critica = false)
        else null
    }
}

class SemEvidencias: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.evidencias)
            Penalidade("Sem Evidências",15,critica = false)
        else null
    }
}
class MenosCommits: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (entrega.quantCommit < 3)
            Penalidade("Menos do que 3 commits",10,critica = false)
        else null
    }
}