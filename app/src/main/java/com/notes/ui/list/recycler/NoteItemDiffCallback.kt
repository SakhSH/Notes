package com.notes.ui.list.recycler

import androidx.recyclerview.widget.DiffUtil
import com.notes.domain.models.Note
import javax.inject.Inject

class NoteItemDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return (oldItem == newItem)
    }
}