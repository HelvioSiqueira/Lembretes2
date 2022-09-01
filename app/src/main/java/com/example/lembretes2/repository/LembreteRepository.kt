package com.example.lembretes2.repository

import com.example.lembretes2.Lembrete

interface LembreteRepository {
    fun save(lembrete: Lembrete)
}