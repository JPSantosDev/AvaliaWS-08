package com.example.avaliaws_08.service

import com.example.avaliaws_08.model.Entrega
import com.example.avaliaws_08.model.Penalidade

interface RegraAvaliacao {
    fun avaliar(entrega: Entrega): Penalidade?
}

class SemReadMe: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.readme)
            Penalidade("Sem ReadME",5,critica = false, pendencia = false)
        else null
    }
}

class SemEvidencias: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.evidencias)
            Penalidade("Sem Evidências",15,critica = false, pendencia = false)
        else null
    }
}
class MenosCommits: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (entrega.quantCommit < 3)
            Penalidade("Menos do que 3 commits",10,critica = false, pendencia = false)
        else null
    }
}

class SemChecklist: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.checklist)
            Penalidade("Sem Checklist",10,critica = false, pendencia = false)
        else null
    }
}

class SemItemFuncional: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.funcional)
            Penalidade("Item Funcional não implementado",20,critica = true, pendencia = false)
        else null
    }
}

class SemApresentacao: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.apresentacao)
            Penalidade("Sem Apresentação",10,critica = false, pendencia = false)
        else null
    }
}

class TempoLongo: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (entrega.tempo > 240)
            Penalidade("Tempo de entrega superior a 240 minutos",5,critica = false, pendencia = false)
        else null
    }
}

class SemCorrecao: RegraAvaliacao{
    override fun avaliar(entrega: Entrega): Penalidade?{
        return if (!entrega.correcao)
            Penalidade("Sem correção ao vivo",0,critica = false,pendencia = true)
        else null
    }
}