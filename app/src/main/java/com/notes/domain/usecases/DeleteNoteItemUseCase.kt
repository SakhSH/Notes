package com.notes.domain.usecases

import com.notes.domain.NotesRepository
import com.notes.domain.models.Note
import javax.inject.Inject

class DeleteNoteItemUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(noteItemId: Long) {
        notesRepository.deleteNoteItem(noteItemId)
    }
}