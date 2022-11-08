package com.app.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.notesapp.R
import com.app.notesapp.databinding.FragmentEditBinding
import com.app.notesapp.databinding.FragmentListBinding
import com.app.notesapp.persistence.Note
import com.app.notesapp.ui.adapter.NoteAdapter
import com.app.notesapp.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListFragment : DaggerFragment(), NoteAdapter.Interaction {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var allNotes: List<Note>

    lateinit var binding: FragmentListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        allNotes = mutableListOf()
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()    // Step 1
        initRecyclerView()  // Step 2
        observerLiveData()  // Step 3
    }

    private fun observerLiveData() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner, Observer { lisOfNotes ->
            lisOfNotes?.let {
                allNotes = it
                noteAdapter.swap(it)
            }
        })
    }

    // Method #4
    private fun initRecyclerView() {
        binding.recyclerView.apply {
            noteAdapter = NoteAdapter(
                allNotes,
                this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = noteAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(binding.recyclerView)
        }
    }

    // Method #5
    private fun setupViewModel() {
        noteViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NoteViewModel::class.java)
    }

    // Method #6
    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        //Swipe recycler view items on RIGHT
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                noteViewModel.delete(allNotes[position])
                val note = allNotes.get(position)
                Toast.makeText(activity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method #7
    override fun onItemSelected(position: Int, item: Note) {
        val navDirection = ListFragmentDirections.actionListFragmentToEditFragment(item)
        findNavController().navigate(navDirection)
    }
}


