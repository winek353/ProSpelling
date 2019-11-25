package com.example.proSpelling.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.proSpelling.R
import com.example.proSpelling.model.Flashcard
import com.example.proSpelling.repository.DatabaseHandler
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity: AppCompatActivity(){
    var isFlashcardShowingObverse = true
    var flashcardList = ArrayList<Flashcard>()
    var flashcardIndex = 0
    var db = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)


        flashcardList = db.readData()

        flashcard.setOnClickListener{
            flipFlashcard()
        }
        next_button.setOnClickListener{
            nextFlashcard()
        }
        delete_button.setOnClickListener{
            deleteFlashcard()
        }

        initializeFirstFlashcard()

    }


    fun flipFlashcard(){
        if(isFlashcardShowingObverse){
            YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).reverse)
            isFlashcardShowingObverse = false
        }
        else{
            YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
            isFlashcardShowingObverse = true
        }
    }

    fun nextFlashcard(){
        flashcardIndex++
        flashcardIndex %= flashcardList.size
        flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
        isFlashcardShowingObverse = true
    }

    fun deleteFlashcard(){
        db.deleteData(flashcardList.get(flashcardIndex).id)
        flashcardList.removeAt(flashcardIndex)
        if(flashcardList.isNotEmpty()){
            nextFlashcard()
        }else{
            flashcardIndex = 0
            onEmptyFlashcardList()
        }
    }

    fun onEmptyFlashcardList(){
        flashcard_main_text.setText("NO DATA!")
        flashcard.setOnClickListener(null)
        next_button.setOnClickListener(null)
        delete_button.setOnClickListener(null)
    }

    fun initializeFirstFlashcard(){
        if(flashcardList.isEmpty()){
            onEmptyFlashcardList()
        }else{
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
            isFlashcardShowingObverse = true
        }
    }

}