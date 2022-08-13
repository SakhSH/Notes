package com.notes.data.mappers

import com.notes.data.NoteDbo
import com.notes.domain.models.Note
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun mapEntityToDbModel(note: Note) = NoteDbo(
        id = note.id,
        title = note.title,
        content = note.content,
        createdAt = note.createdAt,
        modifiedAt = note.modifiedAt
    )

    fun mapDbModelToEntity(noteDbo: NoteDbo) = Note(
        id = noteDbo.id,
        title = noteDbo.title,
        content = noteDbo.content,
        createdAt = noteDbo.createdAt,
        modifiedAt = noteDbo.modifiedAt
    )

    fun mapListDbModelToListEntity(list: List<NoteDbo>) = list.map {
        mapDbModelToEntity(it)
    }
}