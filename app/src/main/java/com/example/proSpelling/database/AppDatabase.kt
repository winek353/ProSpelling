package com.example.proSpelling.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proSpelling.dao.FlashcardDao
import com.example.proSpelling.dao.LeitnerBoxDao
import com.example.proSpelling.entity.Flashcard
import com.example.proSpelling.entity.LeitnerBox


@Database(entities = [Flashcard::class, LeitnerBox::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
    abstract fun leitnerBoxDao(): LeitnerBoxDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "FlashcardDB-1.1"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                //Pre populates database
                                db.execSQL(PREPOPULATE_DATA_SQL)
                                db.execSQL(PREPOPULATE_DATA_LEITNER_BOXES_SQL)
                            }
                        })
                        .build()
                }
            }
            return INSTANCE
        }


        val PREPOPULATE_DATA_SQL = "INSERT INTO Flashcard (obverse,reverse)\n" +
                "VALUES('Obverse test onCreate', 'Reverse test onCreate')," +
                "('Obverse test onCreate2', 'Reverse test onCreate2')"

        val PREPOPULATE_DATA_LEITNER_BOXES_SQL = "INSERT INTO LeitnerBox (imageResource,title,description,frequency)\n" +
                "VALUES(2131099736, 'Level 1', 'Standard box level one box with frequency=1', 1)," +
                "(2131099736, 'Level 2', 'Standard box level with frequency=2', 2)," +
                "(2131099736, 'Level 3', 'Standard box level with frequency=4', 4)," +
                "(2131099736, 'Level 4', 'Standard box level with frequency=9', 9)," +
                "(2131099736, 'Level 5', 'Standard box level with frequency=16', 16)," +
                "(2131099736, 'Level 6', 'Standard box level with frequency=35', 35)," +
                "(2131099736, 'Level 7', 'Standard box level with frequency=64', 64)," +
                "(2131099736, 'Level 8', 'Standard box level for already learned flashcards', 0)"

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}