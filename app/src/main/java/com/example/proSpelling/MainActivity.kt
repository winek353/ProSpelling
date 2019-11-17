package com.example.proSpelling

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCreate.setOnClickListener{
            startActivity(Intent(this, CreateFlashcardActivity::class.java))

        }
        buttonLearn.setOnClickListener{
            startActivity(Intent(this, LearnActivity::class.java))
            //YoYo.with(Techniques.FlipInX).duration(700).playOn(buttonLearn)
            //buttonLearn.setText("Dupa");
//            YoYo.with(Techniques.).duration(700).playOn(buttonLearn)
        }
    }
}
