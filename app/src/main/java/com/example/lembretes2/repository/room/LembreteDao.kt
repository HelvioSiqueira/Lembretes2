package com.example.lembretes2.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.COLUMN_POSITION
import com.example.lembretes2.repository.COLUMN_TITULO
import com.example.lembretes2.repository.TABLE_LEMBRETE

@Dao
interface LembreteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lembrete: Lembrete): Long

    @Update
    fun update(lembrete: Lembrete): Int

    @Delete
    fun delete(lembrete: Lembrete): Int

    @Query("SELECT * FROM $TABLE_LEMBRETE WHERE $COLUMN_TITULO = :title")
    fun lembreteByTitle(title: String): LiveData<Lembrete>

    @Query("SELECT * FROM $TABLE_LEMBRETE WHERE $COLUMN_TITULO LIKE :query ORDER BY $COLUMN_POSITION")
    fun search(query: String): LiveData<List<Lembrete>>

    @Query("SELECT COUNT(*) FROM $TABLE_LEMBRETE")
    fun quant(): Int
}