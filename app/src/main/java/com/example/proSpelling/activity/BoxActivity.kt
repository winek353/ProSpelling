package com.example.proSpelling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proSpelling.R
import com.example.proSpelling.activity.adapter.BoxesAdapter
import com.example.proSpelling.dao.LeitnerBoxDao
import com.example.proSpelling.database.AppDatabase
import com.example.proSpelling.entity.LeitnerBox
import kotlinx.android.synthetic.main.activity_boxes.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class BoxActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boxes)

        recycler_view.adapter =
            BoxesAdapter(emptyList())
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        CoroutineScope(IO).launch {
            val leitnerBoxList: MutableList<LeitnerBox> = getLeitnerBoxes()
            withContext(Main) {
                recycler_view.adapter = BoxesAdapter(leitnerBoxList)
            }
        }
    }

    private suspend fun getLeitnerBoxes(): MutableList<LeitnerBox> {
        val db = AppDatabase.getAppDataBase(context = this)
        val leitnerBoxDao: LeitnerBoxDao? = db?.leitnerBoxDao()
        val leitnerBoxList: MutableList<LeitnerBox> = leitnerBoxDao?.getLeitnerBoxes()?.toMutableList().orEmpty().toMutableList()
        return leitnerBoxList
    }
}