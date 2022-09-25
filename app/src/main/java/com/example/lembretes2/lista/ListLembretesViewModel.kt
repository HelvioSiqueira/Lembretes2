package com.example.lembretes2.lista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class ListLembretesViewModel(private val repository: LembreteRepository) : ViewModel() {

    private val searchTerm = MutableLiveData<String>()
    private var deletedItem = Lembrete()

    private val lembretes = Transformations.switchMap(searchTerm) { searchTerm ->
        repository.search("%$searchTerm%")
    }

    fun getLembretes(): LiveData<List<Lembrete>> = lembretes

    fun search(term: String = "") {
        searchTerm.value = term
    }

    fun delete(lembrete: Lembrete) {
        deletedItem = lembrete
        repository.remove(lembrete)
    }

    fun move(vararg lembretes: Lembrete) {
        lembretes.forEach {
            repository.save(it)
        }
    }

    fun undoDelete() {
        deletedItem.id = 0
        repository.save(deletedItem)
    }
}