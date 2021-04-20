package com.example.proSpelling.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var obverse : String = "",
    var reverse : String = "",
    var idFkLeitnerBox: Int? = null
)