package com.example.proSpelling.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.proSpelling.model.Flashcard

val DATABASE_NAME = "FlashcardDB"
val TABLE_NAME = "Flashcard"
val COL_ID = "id"
val COL_OBVERSE = "obverse"
val COL_REVERSE = "reverse"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_OBVERSE + " VARCHAR(256), " +
                COL_REVERSE + " INTEGER)"

        db?.execSQL(createTable);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(flashcard : Flashcard){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_OBVERSE, flashcard.obverse)
        cv.put(COL_REVERSE, flashcard.reverse)

        var result = db.insert(TABLE_NAME, null, cv)

        if(result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    fun readData() : ArrayList<Flashcard>{
        var list : ArrayList<Flashcard> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst())
            do{
                var flashcard = Flashcard()
                flashcard.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                flashcard.obverse = result.getString(result.getColumnIndex(COL_OBVERSE))
                flashcard.reverse = result.getString(result.getColumnIndex(COL_REVERSE))
                list.add(flashcard)
            }while (result.moveToNext())

        result.close()
        db.close()
        return list
    }

}