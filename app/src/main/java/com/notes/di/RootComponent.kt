package com.notes.di

import com.notes.ui.details.NoteDetailsFragment
import com.notes.ui.list.NoteListFragment
import dagger.Component

@RootScope
@Component(
    dependencies = [
        AppComponent::class,
    ],
    modules = [
        ViewModelModule::class,
        DataModule::class
    ]
)
interface RootComponent {

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent
        ): RootComponent
    }

    fun inject(fragment: NoteListFragment)

    fun inject(fragment: NoteDetailsFragment)
}