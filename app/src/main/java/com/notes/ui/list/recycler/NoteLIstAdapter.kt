package com.notes.ui.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.notes.databinding.ListItemNoteBinding
import com.notes.domain.models.Note
import javax.inject.Inject

class NoteLIstAdapter @Inject constructor(
    callback: NoteItemDiffCallback,
) : ListAdapter<Note, NoteItemViewHolder>(callback) {

    var onNoteItemClickListener: ((Long) -> Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteItemViewHolder(ListItemNoteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(viewHolder: NoteItemViewHolder, position: Int) {
        val note = getItem(position)
        viewHolder.bind(note, onNoteItemClickListener)
    }
}