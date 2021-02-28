package com.example.proSpelling.activity.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proSpelling.R
import com.example.proSpelling.entity.BoxItem
import kotlinx.android.synthetic.main.box_item.view.*

class BoxesAdapter (private val exampleList: List<BoxItem>) :
    RecyclerView.Adapter<BoxesAdapter.BoxesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.box_item,
            parent, false)
        return BoxesViewHolder(
            itemView
        )
    }
    override fun onBindViewHolder(holder: BoxesViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
    }
    override fun getItemCount() = exampleList.size
    class BoxesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2
    }
}