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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        var db = DatabaseHandler(this)
        flashcardList = db.readData()
        flashcardIndex = 0

        flashcard.setOnClickListener{
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

        next_button.setOnClickListener{
            flashcardIndex++
            flashcardIndex %= flashcardList.size
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
            isFlashcardShowingObverse = true
        }

        delete_button.setOnClickListener{
            db.deleteData(flashcardList.get(flashcardIndex).id)
            flashcardList.removeAt(flashcardIndex)
            if(flashcardList.isNotEmpty()){
                flashcardIndex++
                flashcardIndex %= flashcardList.size
                flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
                isFlashcardShowingObverse = true
            }else{
                flashcardIndex = 0
                flashcard_main_text.setText("NO DATA!")
            }

        }

        if(flashcardList.isEmpty()){
            flashcard_main_text.setText("NO DATA!")
            flashcard.setOnClickListener(null)
            next_button.setOnClickListener(null)
            delete_button.setOnClickListener(null)
        }

    }

}