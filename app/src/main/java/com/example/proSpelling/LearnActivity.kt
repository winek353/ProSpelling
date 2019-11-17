package com.example.proSpelling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity: AppCompatActivity(){
    var isFlashcardShowingObverse = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        flashcard.setOnClickListener{
            if(isFlashcardShowingObverse){
                YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
                flashcard_main_text.setText("reverse")
                isFlashcardShowingObverse = false
            }
            else{
                YoYo.with(Techniques.FlipInX).duration(1000).playOn(flashcard)
                flashcard_main_text.setText("obverse")
                isFlashcardShowingObverse = true
            }
        }

    }

}