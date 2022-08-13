package com.notes.domain.usecases

import androidx.lifecycle.LiveData
import com.notes.domain.NotesRepository
import com.notes.domain.models.Note
import javax.inject.Inject

class GetAllItemsUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {

    operator fun invoke(): LiveData<List<Note>> {
        return notesRepository.getAllItems()
    }
}