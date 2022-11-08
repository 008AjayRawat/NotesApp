package com.app.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            Navigation.findNavController(requireActivity(),R.id.container).popBackStack()
        }
    }


    private fun saveNoteToDatabase() {
        (activity as MainActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
            val id:Int = EditFragmentArgs.fromBundle(arguments!!).note?.id!!
            Log.e("DEBUG","saving note $id")

        } else {
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()
            //Delete the note if all fields are empty (this is done by user)
            val id: Int = EditFragmentArgs.fromBundle(arguments!!).note?.id!!
            noteViewModel.deleteById(id)
            Log.e("DEBUG", "deleting note")
        }
    }

    // Method #3
    override fun onDestroyView() {
        super.onDestroyView()
        saveNoteToDatabase()
    }

    // Method #4
    private fun saveNote() {

        //getting the id from bundle , we are using that id to update/edit the note
        val id:Int? = EditFragmentArgs.fromBundle(arguments!!).note?.id

        val note = Note(id!!,binding.editTitle.text.toString(),binding.editDescription.text.toString(),binding.editTag.text.toString())

        //If title is null set Empty Title
        if (binding.editTitle.text.isNullOrEmpty()) {
            note.title = "Empty Title"

            //Call viewmodel to save the data
            noteViewModel.update(note)

        }else{
            //Call viewmodel to save the data
            Log.e("DEBUG","saving note update is called")
            noteViewModel.update(note)
        }
    }

    // Method #5
    fun validations(): Boolean {
        return !(binding.editTitle.text.isNullOrEmpty()
                && binding.editDescription.text.isNullOrEmpty()
                && binding.editTag.text.isNullOrEmpty())
    }


    // Method #6
    private fun setupViewModel() {
        noteViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NoteViewModel::class.java)
    }


    // Method #7
    private fun prepareNoteForEditing() {
    // Getting the note from the bundle
        //Save args plugin is used as i believe bundle is not good for sending large data
        arguments?.let {
            val safeArgs = EditFragmentArgs.fromBundle(it)
            val note = safeArgs.note
            binding.editTitle.setText(note?.title.toString())
            binding.editDescription.setText(note?.description.toString())
            binding.editTag.setText(note?.tag.toString())
        }

    }
}

