package com.example.lembretes2

import java.text.SimpleDateFormat
import java.util.*

data class Lembrete(
    var id: Int = 0,
    var position: Int = 0,
    var titulo: String = "",
    var texto: String = "",
    var prioridade: String = "",
    var data: String = ""
) {
    init {
        data = obterData()
    }

    private fun obterData(): String {
        val date = Calendar.getInstance().time

        val dateTimeFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())

        return dateTimeFormat.format(date).toString()
    }
}
