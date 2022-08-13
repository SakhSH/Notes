package com.notes.ui.list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.notes.databinding.ListItemNoteBinding
import com.notes.domain.models.Note

class NoteItemViewHolder(private val binding: ListItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        note: Note,
        onNoteItemClickListener: ((Long) -> Unit)?,
    ) {

        binding.titleLabel.text = note.title
        binding.contentLabel.text = note.content
        binding.root.setOnClickListener {
            onNoteItemClickListener?.invoke(note.id)
        }
    }

}