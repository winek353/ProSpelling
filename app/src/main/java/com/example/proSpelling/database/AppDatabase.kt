package com.example.proSpelling.database

import android.content.Context
import androidx.room.*
import com.example.proSpelling.dao.FlashcardDao
import com.example.proSpelling.entity.Flashcard

@Database(entities = [Flashcard::class], version = 1)
//@TypeConverters(DateTypeConverter::class, ListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "FlashcardDB-1.1").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}