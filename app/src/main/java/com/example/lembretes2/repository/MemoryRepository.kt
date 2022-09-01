package com.example.lembretes2.repository

import com.example.lembretes2.Lembrete

object MemoryRepository : LembreteRepository {
    private var nextId = 1L
    private val lembretesList = mutableListOf<Lembrete>()

    init {
        save(Lembrete(0, 0, "Testando", "Mais teste", "Urgente", ""))
    }

    override fun save(lembrete: Lembrete) {
        if (lembrete.id == 0L) {
            lembrete.position = nextId
            lembrete.id = nextId++
            lembretesList.add(lembrete)
        }
    }
}