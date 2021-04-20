package com.example.proSpelling.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeitnerBox (
    @PrimaryKey(autoGenerate = true)
    val idLeitnerBox: Int? = null,
    val imageResource: Int,
    val title: String,
    val description: String,
    val frequency: Int
)