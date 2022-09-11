package com.example.lembretes2.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lembretes2.Lembrete
import com.example.lembretes2.repository.DATABASE_NAME
import com.example.lembretes2.repository.DATABASE_VERSION

@Database(entities = [Lembrete::class], version = DATABASE_VERSION)
abstract class LembreteDatabase : RoomDatabase() {

    abstract fun lembreteDao(): LembreteDao

    companion object {
        private var instance: LembreteDatabase? = null

        fun getDatabase(context: Context): LembreteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LembreteDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return instance as LembreteDatabase
        }

        fun destroyInstance() {
            instance = null
        }
    }
}