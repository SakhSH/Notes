package com.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.notes.data.mappers.NoteMapper
import com.notes.domain.NotesRepository
import com.notes.domain.models.Note
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDatabase: NoteDatabase,
    private val mapper: NoteMapper
) : NotesRepository {

    override fun getAllItems(): LiveData<List<Note>> {
        return Transformations.map(
            noteDatabase.noteDao().getAllItems()
        ) {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getNoteItem(noteItemId: Long): Note {
        return mapper.mapDbModelToEntity(noteDatabase.noteDao().getNoteItem(noteItemId))
    }

    override suspend fun insertItem(noteItem: Note) {
        noteDatabase.noteDao().insertItem(mapper.mapEntityToDbModel(noteItem))
    }

    override suspend fun deleteNoteItem(noteItemId: Long) {
        noteDatabase.noteDao().deleteNoteItem(noteItemId)
    }
}