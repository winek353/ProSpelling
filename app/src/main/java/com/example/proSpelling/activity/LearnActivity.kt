package com.example.proSpelling.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.proSpelling.R
import com.example.proSpelling.dao.FlashcardDao
import com.example.proSpelling.database.AppDatabase
import com.example.proSpelling.entity.Flashcard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity: AppCompatActivity(){
    private var isFlashcardShowingObverse = true
    private var flashcardList: MutableList<Flashcard> = emptyList<Flashcard>().toMutableList()
    private var flashcardIndex = 0
    private var db: AppDatabase? = null
    private var flashcardDao: FlashcardDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        save_edition_button.setVisibility(View.GONE)



        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            flashcardDao = db?.flashcardDao()
            flashcardList = flashcardDao?.getFlashcards()?.toMutableList().orEmpty().toMutableList()
            initializeFirstFlashcard()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

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
            edit_obverse.setVisibility(View.GONE)
            edit_reverse.setVisibility(View.GONE)
            save_edition_button.setVisibility(View.GONE)

            flashcardList.get(flashcardIndex).obverse = edit_obverse.text.toString()
            flashcardList.get(flashcardIndex).reverse = edit_reverse.text.toString()

            Observable.fromCallable {
                flashcardDao?.updateFlashcard(flashcardList.get(flashcardIndex))
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }




    }

    fun editFlashcard(){
        edit_obverse.setText(flashcardList.get(flashcardIndex).obverse)
        edit_obverse.setVisibility(View.VISIBLE)

        edit_reverse.setText(flashcardList.get(flashcardIndex).reverse)
        edit_reverse.setVisibility(View.VISIBLE)

        save_edition_button.setVisibility(View.VISIBLE)
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
        Observable.fromCallable {
            flashcardDao?.deleteFlashcard(flashcardList.get(flashcardIndex))
            flashcardList.removeAt(flashcardIndex)
            if(flashcardList.isNotEmpty()){
                nextFlashcard()
            }else{
                flashcardIndex = 0
                onEmptyFlashcardList()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
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