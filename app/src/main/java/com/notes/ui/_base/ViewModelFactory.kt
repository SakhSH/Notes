package com.notes.ui._base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notes.ui.details.NoteDetailsViewModel
import com.notes.ui.list.NoteListViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}