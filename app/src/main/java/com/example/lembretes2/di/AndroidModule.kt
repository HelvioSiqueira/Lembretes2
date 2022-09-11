package com.example.lembretes2.di

import com.example.lembretes2.adicionar.LembreteFormViewModel
import com.example.lembretes2.lista.ListLembretesViewModel
import com.example.lembretes2.repository.LembreteRepository
import com.example.lembretes2.repository.room.LembreteDatabase
import com.example.lembretes2.repository.room.RoomRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { this }

    single {
        RoomRepository(LembreteDatabase.getDatabase(context = get())) as LembreteRepository
    }

    viewModel {
        ListLembretesViewModel(repository = get())
    }

    viewModel {
        LembreteFormViewModel(repository = get())
    }
}