package com.app.notesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.app.notesapp.persistence.NoteDao
import com.app.notesapp.persistence.Note
import kotlinx.coroutines.*
import javax.inject.Inject

class NoteRepository @Inject constructor(val noteDao: NoteDao) {

    //function to insert note in database
    fun insert(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insert(note)
        }
    }

    //function to delete note in database
    fun delete(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
    }

    //function to delete note by Id in database
    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteById(id)
        }
    }

    //function to update note in database
    fun update(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(note)
            Log.e("DEBUG", "update is called in repo")

        }
    }

    //function to get all notes in database
    fun getAllNotes(): LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }
}