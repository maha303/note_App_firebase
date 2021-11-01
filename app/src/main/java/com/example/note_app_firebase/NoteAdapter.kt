package com.example.note_app_firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_firebase.databinding.ItemRowBinding

class NoteAdapter (private val activity: MainActivity):RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
    private var notes= emptyList<Note>()
    class ItemViewHolder (val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            tvNote.text=note.noteText
            ivEdit.setOnClickListener {
                activity.raiseDialog(note.id)
            }
            ivDelete.setOnClickListener {

                activity.mainViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount()=notes.size

    fun update(notes:List<Note>){
        this.notes=notes
        notifyDataSetChanged()
    }
}