package com.example.proSpelling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proSpelling.R
import com.example.proSpelling.activity.adapter.BoxesAdapter
import com.example.proSpelling.entity.BoxItem
import kotlinx.android.synthetic.main.activity_boxes.*

class BoxActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boxes)
        val exampleList = generateDummyList(500)
        recycler_view.adapter =
            BoxesAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
    private fun generateDummyList(size: Int): List<BoxItem> {
        val list = ArrayList<BoxItem>()
        for (i in 0 until size) {
            val drawable = when (i % 2) {
                0 -> R.drawable.ic__language
                else -> R.drawable.ic_smile
            }
            val item = BoxItem(
                drawable,
                "Item $i",
                "Line 2"
            )
            list += item
        }
        return list
    }
}