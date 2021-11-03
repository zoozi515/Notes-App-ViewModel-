package com.example.w7_d1_livedata_and_viewmodel.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.w7_d1_livedata_and_viewmodel.MainActivity
import com.example.w7_d1_livedata_and_viewmodel.database.Note
import com.example.w7_d1_livedata_and_viewmodel.databinding.NoteRowBinding

class NoteAdapter(private val activity: MainActivity): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
    private var notes = emptyList<Note>()

    class ItemViewHolder(val binding: NoteRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ItemViewHolder {
        return ItemViewHolder(
            NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.ItemViewHolder, position: Int) {
        val item = notes[position]

        holder.binding.apply {
            noteTextView.text = item.noteText //name of the entity column
            if (position % 2 == 0) {
                holderLinearLayout.setBackgroundColor(Color.GRAY)
            }
            ibEditNote.setOnClickListener {
                activity.raiseDialog(item.id,true)
            }
            ibDeleteNote.setOnClickListener {
                activity.raiseDialog(item.id,false)
                //activity.mainViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun updateRecycleView(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    // messages = items
}