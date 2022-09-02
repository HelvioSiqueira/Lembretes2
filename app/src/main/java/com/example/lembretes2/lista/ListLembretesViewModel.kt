package com.example.lembretes2.lista

import androidx.lifecycle.ViewModel
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class ListLembretesViewModel(private val repository: LembreteRepository) : ViewModel() {

    fun salvar(lembrete: Lembrete) {
        repository.save(lembrete)
    }

    fun buscar(term: String): List<Lembrete> {

        var lista: List<Lembrete> = emptyList()

        repository.search(term){
            lista = it
        }

        return lista
    }
}