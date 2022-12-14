package com.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.notes.domain.models.Note
import java.time.LocalDateTime

@Entity(tableName = "notes")
data class NoteDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "createdAt")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "modifiedAt")
    val modifiedAt: LocalDateTime,
)