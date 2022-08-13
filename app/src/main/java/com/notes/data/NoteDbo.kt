package com.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notes")
data class NoteDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = UNDEFINED_ID,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "modifiedAt")
    val modifiedAt: LocalDateTime,
) {
    companion object {

        const val UNDEFINED_ID = 0L
    }
}