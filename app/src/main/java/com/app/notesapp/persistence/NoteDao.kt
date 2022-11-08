package com.app.notesapp.persistence
import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM tbl_note WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM tbl_note")
    fun getAllNotes():LiveData<List<Note>>
}