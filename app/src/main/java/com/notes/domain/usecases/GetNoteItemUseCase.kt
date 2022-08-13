package com.notes.domain.usecases

import com.notes.domain.NotesRepository
import com.notes.domain.models.Note
import javax.inject.Inject

class GetNoteItemUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(noteItemId: Long): Note {
        return notesRepository.getNoteItem(noteItemId)
    }
}