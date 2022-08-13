package com.notes.domain.usecases

import com.notes.domain.NotesRepository
import com.notes.domain.models.Note
import javax.inject.Inject

class InsetItemUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        return notesRepository.insertItem(note)
    }
}