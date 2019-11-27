package com.example.proSpelling.activity

import android.os.Bundle
import android.view.View
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

        save_edition_button.setVisibility(View.GONE)
        flashcardList = db.readData()

        setButtonsActive()

        initializeFirstFlashcard()

    }

    fun updateFlashcard(){
        edit_obverse.setVisibility(View.GONE)
        edit_reverse.setVisibility(View.GONE)
        save_edition_button.setVisibility(View.GONE)

        flashcardList.get(flashcardIndex).obverse = edit_obverse.text.toString()
        flashcardList.get(flashcardIndex).reverse = edit_reverse.text.toString()

        db.updateData(flashcardList.get(flashcardIndex))
        refreshFlashcardData()
        setButtonsActive()
    }


    fun editFlashcard(){
        disableButtons()

        edit_obverse.setText(flashcardList.get(flashcardIndex).obverse)
        edit_obverse.setVisibility(View.VISIBLE)

        edit_reverse.setText(flashcardList.get(flashcardIndex).reverse)
        edit_reverse.setVisibility(View.VISIBLE)

        save_edition_button.setVisibility(View.VISIBLE)
    }

    fun refreshFlashcardData(){
        flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
        isFlashcardShowingObverse = true
    }

    fun flipFlashcard(){
        YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
        if(isFlashcardShowingObverse){
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).reverse)
            isFlashcardShowingObverse = false
        }
        else{
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
            isFlashcardShowingObverse = true
        }
    }

    fun nextFlashcard(){
        flashcardIndex++
        flashcardIndex %= flashcardList.size
        refreshFlashcardData()
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
        disableButtons()
    }

    fun initializeFirstFlashcard(){
        if(flashcardList.isEmpty()){
            onEmptyFlashcardList()
        }else{
            flashcard_main_text.setText(flashcardList.get(flashcardIndex).obverse)
            isFlashcardShowingObverse = true
        }
    }

    fun disableButtons(){
        flashcard.setOnClickListener(null)
        next_button.setOnClickListener(null)
        delete_button.setOnClickListener(null)
        edit_button.setOnClickListener(null)
    }

    fun setButtonsActive(){
        flashcard.setOnClickListener{
            flipFlashcard()
        }
        next_button.setOnClickListener{
            nextFlashcard()
        }
        delete_button.setOnClickListener{
            deleteFlashcard()
        }
        edit_button.setOnClickListener{
            editFlashcard()
        }
        save_edition_button.setOnClickListener{
            updateFlashcard()
        }
    }

}