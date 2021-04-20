package com.example.proSpelling.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proSpelling.entity.Flashcard
import com.example.proSpelling.entity.LeitnerBox

data class LeitnerBoxWithFlashcards (
    @Embedded val leitnerBox: LeitnerBox,
    @Relation(
        parentColumn = "idLeitnerBox",
        entityColumn = "idFkLeitnerBox"
    )
    val flashcards: List<Flashcard>
)
