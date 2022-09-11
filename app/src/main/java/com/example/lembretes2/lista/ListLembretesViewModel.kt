package com.example.lembretes2.lista

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class ListLembretesViewModel(private val repository: LembreteRepository) : ViewModel() {

    fun salvar(lembrete: Lembrete) {
        repository.save(lembrete)
    }

    fun buscar(term: String): LiveData<List<Lembrete>> {

        var lista: List<Lembrete> = emptyList()

        return repository.search(term)
    }
}