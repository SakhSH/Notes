package com.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.domain.usecases.DeleteNoteItemUseCase
import com.notes.domain.usecases.GetAllItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteNoteItemUseCase: DeleteNoteItemUseCase
) : ViewModel() {

    private val _isNavigateToNoteCreation = MutableLiveData<NoteNavigationAction>()
    val isNavigateToNoteCreation: LiveData<NoteNavigationAction> = _isNavigateToNoteCreation

    val getList by lazy {
        getAllItemsUseCase.invoke()
    }

    fun deleteNoteItem(noteItemId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteNoteItemUseCase.invoke(noteItemId)
            }
        }
    }

    fun onEditNoteClick(noteItemId: Long) {
        _isNavigateToNoteCreation.value = NoteNavigationAction.Edit(noteItemId)
    }

    fun onCreateNoteClick() {
        _isNavigateToNoteCreation.value = NoteNavigationAction.Add
    }

    fun onNavigationSuccess() {
        _isNavigateToNoteCreation.value = NoteNavigationAction.Clear
    }
}