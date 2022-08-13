package com.notes.di

import com.notes.data.NotesRepositoryImpl
import com.notes.domain.NotesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @RootScope
    @Binds
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}