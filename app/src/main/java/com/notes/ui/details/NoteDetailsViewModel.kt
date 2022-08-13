package com.notes.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.LocalDateTimeConverter
import com.notes.domain.models.Note
import com.notes.domain.usecases.GetNoteItemUseCase
import com.notes.domain.usecases.InsetItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
    private val getNoteItemUseCase: GetNoteItemUseCase,
    private val insertItemUseCase: InsetItemUseCase
) : ViewModel() {

    private val _errorInputTitle = MutableLiveData<Boolean>()
    val errorInputTitle: LiveData<Boolean>
        get() = _errorInputTitle

    private val _errorInputContent = MutableLiveData<Boolean>()
    val errorInputContent: LiveData<Boolean>
        get() = _errorInputContent

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    private val _noteItem = MutableLiveData<Note>()
    val noteItem: LiveData<Note>
        get() = _noteItem

    fun getNoteItem(noteItemId: Long) {
        viewModelScope.launch {
            var item: Note?
            withContext(Dispatchers.IO) {
                item = getNoteItemUseCase(noteItemId)
            }
            item?.let {
                _noteItem.value = it
            }
        }
    }

    fun addNoteItem(inputTitle: String?, inputContent: String?) {
        val title = parseText(inputTitle)
        val content = parseText(inputContent)
        val fieldsValid = validateInput(title, content)
        if (fieldsValid) {
            viewModelScope.launch {
                val dataTime = LocalDateTimeConverter().localDateTimeToString(
                    LocalDateTime.now()
                )
                val newNote = Note(
                    title = title,
                    content = content,
                    createdAt = LocalDateTimeConverter().stringToLocalDateTime(dataTime),
                    modifiedAt = LocalDateTimeConverter().stringToLocalDateTime(dataTime)
                )
                insertItemUseCase(newNote)
                navigateToNoteListFragment()
            }
        }
    }

    fun editNoteItem(inputTitle: String?, inputContent: String?, note: Note) {
        val title = parseText(inputTitle)
        val content = parseText(inputContent)
        val fieldsValid = validateInput(title, content)
        if (fieldsValid) {
            viewModelScope.launch {
                val dataTime = LocalDateTimeConverter().localDateTimeToString(
                    LocalDateTime.now()
                )
                val newNote = note.copy(
                    title = title,
                    content = content,
                    modifiedAt = LocalDateTimeConverter().stringToLocalDateTime(dataTime)
                )
                insertItemUseCase(newNote)
                navigateToNoteListFragment()
            }
        }
    }

    private fun parseText(inputTitle: String?): String {
        return inputTitle?.trim().orEmpty()
    }

    private fun validateInput(title: String, content: String): Boolean {
        var result = true
        if (title.isBlank()) {
            _errorInputTitle.value = true
            result = false
        }
        if (content.isBlank()) {
            _errorInputContent.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputTitle() {
        _errorInputTitle.value = false
    }

    fun resetErrorInputContent() {
        _errorInputContent.value = false
    }

    private fun navigateToNoteListFragment() {
        _isFinished.value = true
    }

    fun navigateToNoteListFragmentFinished() {
        _isFinished.value = false
    }
}