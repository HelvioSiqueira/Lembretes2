package com.example.lembretes2.repository.room


import androidx.lifecycle.LiveData
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.LembreteRepository

class RoomRepository(database: LembreteDatabase): LembreteRepository {

    private val lembreteDao = database.lembreteDao()

    override fun save(lembrete: Lembrete) {
        if(lembrete.id == 0L){

            lembrete.position = lembreteDao.quant().toLong() + 1

            val id = lembreteDao.insert(lembrete)
            lembrete.id = id
        }
    }

    override fun remove(lembrete: Lembrete) {
        lembreteDao.delete(lembrete)
    }

    override fun lembreteByTitle(title: String): LiveData<Lembrete> {
        return lembreteDao.lembreteByTitle(title)
    }

    override fun search(term: String): LiveData<List<Lembrete>> {
        return lembreteDao.search(term)
    }

    override fun quant(): Int {
        return lembreteDao.quant()
    }
}