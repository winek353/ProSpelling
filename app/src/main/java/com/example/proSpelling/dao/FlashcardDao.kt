package com.example.proSpelling.dao

import androidx.room.*
import com.example.proSpelling.entity.Flashcard

@Dao
interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlashcard(flashcard: Flashcard)

    @Update
    fun updateFlashcard(flashcard: Flashcard)

    @Delete
    fun deleteFlashcard(flashcard: Flashcard)

//    @Query("SELECT * FROM Flashcard WHERE name == :name")
//    fun getFlashcardByName(name: String): List<Flashcard>
//
    @Query("SELECT * FROM Flashcard")
    fun getFlashcards(): List<Flashcard>
}