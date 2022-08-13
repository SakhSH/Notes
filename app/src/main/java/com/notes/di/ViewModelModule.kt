package com.notes.di

import androidx.lifecycle.ViewModel
import com.notes.ui.details.NoteDetailsViewModel
import com.notes.ui.list.NoteListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    fun bindNoteListViewModel(viewModel: NoteListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailsViewModel::class)
    fun bindNoteDetailsViewModel(viewModel: NoteDetailsViewModel): ViewModel
}