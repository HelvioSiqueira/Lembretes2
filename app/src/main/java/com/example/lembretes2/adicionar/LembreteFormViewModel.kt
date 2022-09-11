package com.example.lembretes2.adicionar

import androidx.lifecycle.ViewModel
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class LembreteFormViewModel(private val repository: LembreteRepository): ViewModel() {

    fun salvar(lembrete: Lembrete) {
        repository.save(lembrete)
    }
}