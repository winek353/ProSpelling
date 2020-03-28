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
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity : AppCompatActivity() {
    private var isFlashcardShowingObverse = true
    private var flashcardList: MutableList<Flashcard> = emptyList<Flashcard>().toMutableList()
    private var flashcardIndex = 0
    private var flashcardDao: FlashcardDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        save_edition_button.visibility = View.GONE

        val db = AppDatabase.getAppDataBase(context = this)
        flashcardDao = db?.flashcardDao()

        Completable.fromAction {
            flashcardList = flashcardDao?.getFlashcards()?.toMutableList().orEmpty().toMutableList()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { initializeFirstFlashcard() }
            .subscribe()
    }

    fun updateFlashcard() {
        edit_obverse.visibility = View.GONE
        edit_reverse.visibility = View.GONE
        save_edition_button.visibility = View.GONE

        flashcardList.get(flashcardIndex).obverse = edit_obverse.text.toString()
        flashcardList.get(flashcardIndex).reverse = edit_reverse.text.toString()

        Observable.fromCallable {
            flashcardDao?.updateFlashcard(flashcardList.get(flashcardIndex))
            refreshFlashcardData()
            setButtonsActive()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }


    fun editFlashcard() {
        disableButtons()

        edit_obverse.setText(flashcardList.get(flashcardIndex).obverse)
        edit_obverse.visibility = View.VISIBLE

        edit_reverse.setText(flashcardList.get(flashcardIndex).reverse)
        edit_reverse.visibility = View.VISIBLE

        save_edition_button.visibility = View.VISIBLE
    }

    fun refreshFlashcardData() {
        flashcard_main_text.text = flashcardList.get(flashcardIndex).obverse
        isFlashcardShowingObverse = true
    }

    fun flipFlashcard() {
        YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
        if (isFlashcardShowingObverse) {
            flashcard_main_text.text = flashcardList.get(flashcardIndex).reverse
            isFlashcardShowingObverse = false
        } else {
            flashcard_main_text.text = flashcardList.get(flashcardIndex).obverse
            isFlashcardShowingObverse = true
        }
    }

    fun nextFlashcard() {
        flashcardIndex++
        flashcardIndex %= flashcardList.size
        refreshFlashcardData()
    }

    fun deleteFlashcard() {
        Completable.fromAction {
            flashcardDao?.deleteFlashcard(flashcardList.get(flashcardIndex))
            flashcardList.removeAt(flashcardIndex)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                if (flashcardList.isNotEmpty()) {
                    nextFlashcard()
                } else {
                    flashcardIndex = 0
                    onEmptyFlashcardList()
                }
            }
            .subscribe()
    }

    fun onEmptyFlashcardList() {
        flashcard_main_text.text = "NO DATA!"
        disableButtons()
    }

    fun initializeFirstFlashcard() {
        if (flashcardList.isEmpty()) {
            onEmptyFlashcardList()
        } else {
            flashcard_main_text.text = flashcardList.get(flashcardIndex).obverse
            isFlashcardShowingObverse = true
            setButtonsActive()
        }
    }

    fun disableButtons() {
        flashcard.setOnClickListener(null)
        next_button.setOnClickListener(null)
        delete_button.setOnClickListener(null)
        edit_button.setOnClickListener(null)
    }

    fun setButtonsActive() {
        flashcard.setOnClickListener {
            flipFlashcard()
        }
        next_button.setOnClickListener {
            nextFlashcard()
        }
        delete_button.setOnClickListener {
            deleteFlashcard()
        }
        edit_button.setOnClickListener {
            editFlashcard()
        }
        save_edition_button.setOnClickListener {
            updateFlashcard()
        }
    }

}