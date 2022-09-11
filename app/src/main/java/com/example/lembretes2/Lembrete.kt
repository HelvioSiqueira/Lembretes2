package com.example.lembretes2

import java.text.SimpleDateFormat
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.example.lembretes2.repository.COLUMN_ID
import com.example.lembretes2.repository.TABLE_LEMBRETE


@Entity(tableName = TABLE_LEMBRETE)
data class Lembrete(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0L,
    var position: Long = 0L,
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

        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return dateTimeFormat.format(date).toString()
    }
}
