package com.example.lembretes2.repository

import androidx.lifecycle.LiveData
import com.example.lembretes2.Lembrete

interface LembreteRepository {
    fun save(lembrete: Lembrete)
    fun remove(lembrete: Lembrete)
    fun lembreteByTitle(title: String): LiveData<Lembrete>
    fun search(term: String): LiveData<List<Lembrete>>
    fun quant() : Int
}