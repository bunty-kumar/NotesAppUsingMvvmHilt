package com.bunty.notesappusingmvvmhilt.views.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bunty.notesappusingmvvmhilt.databinding.NoteItemBinding
import com.bunty.notesappusingmvvmhilt.models.NoteResponse

class NotesAdapter(private val onNoteClicked: (NoteResponse) -> Unit) :
    ListAdapter<NoteResponse, NotesAdapter.NotesViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class NotesViewHolder(private val noteItemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(noteItemBinding.root) {

        fun bind(noteResponse: NoteResponse) {
            noteItemBinding.title.text = noteResponse.title
            noteItemBinding.desc.text = noteResponse.description
            noteItemBinding.root.setOnClickListener {
                onNoteClicked(noteResponse)
            }
        }

    }

    class DiffUtil() : androidx.recyclerview.widget.DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

    }

}