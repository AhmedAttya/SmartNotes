package com.tresole.smartnotes.main

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.tresole.smartnotes.R
import com.tresole.smartnotes.repo.Note


 class NoteAdapter(private val listnotes: List<Note>,val clickListener :Noteclicklistener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val context =parent.context
        val inflater= LayoutInflater.from(context)
        val itemview =inflater.inflate(R.layout.note_recyclerview_item,parent,false)
        return ViewHolder(itemview,clickListener)
    }


    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        holder.bind(listnotes[position])
    }


    override fun getItemCount(): Int {
        return listnotes.size
    }
    inner class ViewHolder (itemview : View,val clickListener :Noteclicklistener) : RecyclerView.ViewHolder(itemview) {
        private val cardView :CardView=itemview.findViewById(R.id.noteitem)
        private val titletextview: TextView = itemview.findViewById(R.id.title)
        private val bodytextview: TextView =itemview.findViewById(R.id.body)

        fun bind(Note: Note) {
            val title = titletextview
            val body = bodytextview
            title.text = Note.title
            body.text = Note.notebody
            cardView.setOnClickListener{
                clickListener.onclick(Note)
        }

    }
}}
interface Noteclicklistener{
 fun onclick(note: Note)
}
