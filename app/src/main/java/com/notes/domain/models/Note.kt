package com.notes.domain.models

import java.time.LocalDateTime

data class Note(

    val id: Long = UNDEFINED_ID,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    companion object {

        const val UNDEFINED_ID = 0L
    }
}

