package com.notes.ui.details

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.di.DependencyManager
import com.notes.domain.models.Note
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui._base.ViewModelFactory
import javax.inject.Inject

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NoteDetailsViewModel by viewModels { viewModelFactory }

    private var noteItemId: Long = Note.UNDEFINED_ID
    private var screenMode: String = MODE_UNKNOWN

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DependencyManager.getComponent().inject(this)
    }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        addTextChangeListeners()
        observeViewModel()
        launchRightMode()
    }

    private fun addTextChangeListeners() {
        viewBinding?.etTitle?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputTitle()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        viewBinding?.etContent?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputContent()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewBinding?.let {
            with(it) {
                viewModel.getNoteItem(noteItemId)
                viewModel.noteItem.observe(viewLifecycleOwner) { noteItem ->
                    etTitle.setText(noteItem.title)
                    etContent.setText(noteItem.content)
                    saveButton.setOnClickListener {
                        viewModel.editNoteItem(
                            etTitle.text?.toString(),
                            etContent.text?.toString(),
                            noteItem
                        )
                    }
                }
            }
        }

    }

    private fun launchAddMode() {
        viewBinding?.let {
            with(it) {
                saveButton.setOnClickListener {
                    viewModel.addNoteItem(
                        etTitle.text?.toString(),
                        etContent.text?.toString()
                    )
                }
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        screenMode = args.getString(SCREEN_MODE).orEmpty()
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }
        if (screenMode == MODE_EDIT) {
            noteItemId = args.getLong(NOTE_ITEM_ID, Note.UNDEFINED_ID)
            if (noteItemId == Note.UNDEFINED_ID) {
                throw RuntimeException("Param note item id is absent")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputTitle.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Invalid title"
            } else {
                null
            }
            viewBinding?.etTitle?.error = message
        }
        viewModel.errorInputContent.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Invalid content"
            } else {
                null
            }
            viewBinding?.etContent?.error = message
        }
        viewModel.isFinished.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.navigateToNoteListFragmentFinished()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val NOTE_ITEM_ID = "extra_note_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): NoteDetailsFragment {
            return NoteDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(noteItemId: Long): NoteDetailsFragment {
            return NoteDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putLong(NOTE_ITEM_ID, noteItemId)
                }
            }
        }
    }
}