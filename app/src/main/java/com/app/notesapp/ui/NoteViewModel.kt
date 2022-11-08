package com.app.notesapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.notesapp.persistence.Note
import com.app.notesapp.repository.NoteRepository
import javax.inject.Inject

class NoteViewModel @Inject constructor(val noteRepository: NoteRepository) : ViewModel() {

    fun insert(note: Note) {
        return noteRepository.insert(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deleteById(id:Int){
        noteRepository.deleteById(id)
    }

    fun update(note: Note) {
        Log.e("DEBUG","update is called in viewmodel")
        noteRepository.update(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        Log.e("DEBUG", "View model getallnotes")
        return noteRepository.getAllNotes()
    }


}