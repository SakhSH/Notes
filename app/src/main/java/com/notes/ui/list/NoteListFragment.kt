package com.notes.ui.list

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.notes.R
import com.notes.data.LocalDateTimeConverter
import com.notes.databinding.FragmentNoteListBinding
import com.notes.di.*
import com.notes.ui._base.*
import com.notes.ui.details.NoteDetailsFragment
import com.notes.ui.list.recycler.NoteLIstAdapter
import javax.inject.Inject
import kotlin.concurrent.thread

class NoteListFragment : ViewBindingFragment<FragmentNoteListBinding>(
    FragmentNoteListBinding::inflate
) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NoteListViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var recyclerViewAdapter: NoteLIstAdapter

    private var pressedTime: Long = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DependencyManager.getComponent().inject(this)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (pressedTime + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.on_back_pressed_text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    pressedTime = System.currentTimeMillis()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteListBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)
        viewBinding.list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )
        setupRecyclerView(viewBinding)
        viewBinding.createNoteButton.setOnClickListener {
            viewModel.onCreateNoteClick()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getList.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it)
        }

        viewModel.isNavigateToNoteCreation.observe(viewLifecycleOwner) {
            when(it) {
                NoteNavigationAction.Add -> {
                    viewModel.onNavigationSuccess()
                    findImplementationOrThrow<FragmentNavigator>()
                        .navigateTo(
                            NoteDetailsFragment.newInstanceAddItem()
                        )
                }
                is NoteNavigationAction.Edit -> {
                    viewModel.onNavigationSuccess()
                    findImplementationOrThrow<FragmentNavigator>()
                        .navigateTo(
                            NoteDetailsFragment.newInstanceEditItem(it.noteItemId)
                        )
                }
                NoteNavigationAction.Clear -> {}
            }
        }
    }

    private fun setupRecyclerView(binding: FragmentNoteListBinding) {
        binding.list.adapter = recyclerViewAdapter
        recyclerViewAdapter.onNoteItemClickListener = {
            viewModel.onEditNoteClick(it)
        }
        setupSwipeListener(binding.list)
    }

    private fun setupSwipeListener(rvNoteList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = recyclerViewAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNoteItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvNoteList)
    }
}