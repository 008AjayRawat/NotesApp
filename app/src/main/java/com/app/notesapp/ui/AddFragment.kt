package com.app.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.notesapp.R
import com.app.notesapp.databinding.FragmentAddBinding
import com.app.notesapp.persistence.Note
import com.app.notesapp.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class AddFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var noteViewModel: NoteViewModel

    lateinit var binding:FragmentAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        binding.btnAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.container).popBackStack()
        }
    }

    private fun saveNoteToDatabase() {
        (activity as MainActivity?)?.showFloatingButton()
        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
        } else
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveNoteToDatabase()
    }


    // Method #5
    private fun saveNote() {
        val note = Note(0,binding.addTitle.text.toString(),binding.addDescription.text.toString(),binding.addTag.text.toString())

        //If title is null set Empty Title
        if (binding.addTitle.text.isNullOrEmpty()) {
           note.title = "Empty Title"
            //Call viewmodel to save the data
            noteViewModel.insert(note)

        }else{
            //Call viewmodel to save the data
            noteViewModel.insert(note)
        }
    }

    private fun validations(): Boolean {
        return !(binding.addTitle.text.isNullOrEmpty()
                && binding.addDescription.text.isNullOrEmpty()
                && binding.addTag.text.isNullOrEmpty())
    }


    // Method #7
    private fun setupViewModel() {
        noteViewModel = ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }
}