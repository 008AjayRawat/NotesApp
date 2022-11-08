package com.app.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.notesapp.R
import com.app.notesapp.databinding.FragmentEditBinding
import com.app.notesapp.persistence.Note
import com.app.notesapp.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class EditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var noteViewModel: NoteViewModel

    lateinit var binding: FragmentEditBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareNoteForEditing()
        setupViewModel()

        binding.btnEdit.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
        }
    }


    private fun saveNoteToDatabase() {
        (activity as MainActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
            val id: Int = EditFragmentArgs.fromBundle(arguments!!).note?.id!!
            Log.e("DEBUG", "saving note $id")

        } else {
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()
            //Delete the note if all fields are empty (this is done by user)
            val id: Int = EditFragmentArgs.fromBundle(arguments!!).note?.id!!
            noteViewModel.deleteById(id)
            Log.e("DEBUG", "deleting note")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveNoteToDatabase()
    }

    private fun saveNote() {
        //getting the id from bundle , we are using that id to update/edit the note
        val previousNote = EditFragmentArgs.fromBundle(arguments!!).note
        val id: Int? = EditFragmentArgs.fromBundle(arguments!!).note?.id

        val note = Note(
            id!!,
            binding.editTitle.text.toString(),
            binding.editDescription.text.toString(),
            binding.editTag.text.toString(),
            previousNote?.createdDate,
            System.currentTimeMillis().toString()
        )

        //If title is null set Empty Title
        if (binding.editTitle.text.isNullOrEmpty()) {
            note.title = "Empty Title"

            //update the data
            noteViewModel.update(note)

        } else {
            //update the data
            Log.e("DEBUG", "Note update is called")
            noteViewModel.update(note)
        }
    }

    private fun validations(): Boolean {
        return !(binding.editTitle.text.isNullOrEmpty()
                && binding.editDescription.text.isNullOrEmpty())
    }


    private fun setupViewModel() {
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }


    private fun prepareNoteForEditing() {
        // Getting the note from the bundle
        arguments?.let {
            val safeArgs = EditFragmentArgs.fromBundle(it)
            val note = safeArgs.note
            binding.editTitle.setText(note?.title.toString())
            binding.editDescription.setText(note?.description.toString())
            binding.editTag.setText(note?.tag.toString())
        }

    }
}

