package com.example.proSpelling.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proSpelling.R
import com.example.proSpelling.dao.FlashcardDao
import com.example.proSpelling.database.AppDatabase
import com.example.proSpelling.entity.Flashcard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_flashcard.*

class CreateFlashcardActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var flashcardDao: FlashcardDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_flashcard)

        createButton.setOnClickListener {
            if(obverse_text.text.toString().isNotEmpty() && reverse_text.text.toString().isNotEmpty()) {
                var flashcard = Flashcard(obverse = obverse_text.text.toString(), reverse =  reverse_text.text.toString())

                Observable.fromCallable {
                    db = AppDatabase.getAppDataBase(context = this)
                    flashcardDao = db?.flashcardDao()
                    flashcardDao?.insertFlashcard(flashcard)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()

            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}