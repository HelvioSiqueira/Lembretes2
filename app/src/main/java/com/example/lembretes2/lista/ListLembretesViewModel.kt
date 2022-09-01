package com.example.lembretes2.lista

import androidx.lifecycle.ViewModel
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class ListLembretesViewModel(private val repository: LembreteRepository): ViewModel() {

    fun salvar(){
        repository.save(Lembrete(0, 0, "Testando", "Mais teste", "Urgente", ""))
    }
}