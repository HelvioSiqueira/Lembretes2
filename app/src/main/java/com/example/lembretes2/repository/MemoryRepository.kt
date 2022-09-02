package com.example.lembretes2.repository

import com.example.lembretes2.Lembrete

object MemoryRepository : LembreteRepository {
    private var nextId = 1L
    private val lembretesList = mutableListOf<Lembrete>()

    init {
        save(Lembrete(0, 0, "Testando1", "Mais teste", "Urgente", ""))
        save(Lembrete(0, 0, "Testando2", "Mais teste", "Urgente", ""))
        save(Lembrete(0, 0, "Testando2", "Mais teste", "Importante", ""))
    }

    override fun save(lembrete: Lembrete) {
        if (lembrete.id == 0L) {
            lembrete.position = nextId
            lembrete.id = nextId++
            lembretesList.add(lembrete)
        }
    }

    override fun search(term: String, callback: (List<Lembrete>) -> Unit) {
        callback(
            if(term.isEmpty()) lembretesList
        else lembretesList.filter {
            it.titulo.uppercase().contains(term.uppercase())
            }
        )
    }
}