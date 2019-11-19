package com.example.proSpelling.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proSpelling.R
import com.example.proSpelling.model.Flashcard
import com.example.proSpelling.repository.DatabaseHandler
import kotlinx.android.synthetic.main.activity_create_flashcard.*

class CreateFlashcardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_flashcard)

        createButton.setOnClickListener {
            if(obverse_text.text.toString().length > 0 && reverse_text.text.toString().length > 0) {
                var flashcard = Flashcard(obverse_text.text.toString(), reverse_text.text.toString())
                var db = DatabaseHandler(this)
                db.insertData(flashcard)
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}