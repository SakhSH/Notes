package com.notes.domain

import androidx.lifecycle.LiveData
import com.notes.domain.models.Note

interface NotesRepository {

    fun getAllItems(): LiveData<List<Note>>

    suspend fun getNoteItem(noteItemId: Long): Note

    suspend fun insertItem(noteItem: Note)

    suspend fun deleteNoteItem(noteItemId: Long)
}