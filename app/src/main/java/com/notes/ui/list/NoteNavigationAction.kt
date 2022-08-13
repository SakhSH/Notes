package com.notes.ui.list

sealed class NoteNavigationAction {

    object Add : NoteNavigationAction()

    data class Edit(
        val noteItemId: Long
    ) : NoteNavigationAction()

    object Clear : NoteNavigationAction()
}
