package com.example.proSpelling.dao

import androidx.room.*
import com.example.proSpelling.entity.Flashcard
import com.example.proSpelling.entity.LeitnerBox

@Dao
interface LeitnerBoxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlashcard(flashcard: Flashcard)

//    @Transaction
//    @Query("SELECT * FROM LeitnerBox WHERE idLeitnerBox= :_idLeitnerBox")
//    fun getFlashcards(_idLeitnerBox: Int): List<Flashcard>

    @Query("SELECT * FROM LeitnerBox")
    fun getLeitnerBoxes(): List<LeitnerBox>
}