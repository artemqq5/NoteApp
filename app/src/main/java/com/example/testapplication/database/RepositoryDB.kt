package com.example.testapplication.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.testapplication.MainActivity.Companion.MAIN_CONTEXT

class RepositoryDB {

    private val dataBase by lazy {
        Room.databaseBuilder(
            MAIN_CONTEXT!!,
            NoteDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    private val daoManager = dataBase.noteDao()

    // get all notes
    fun getAllNotes(): LiveData<List<TableNote>> {
        return daoManager.getAll()
    }

    // delete some note
    suspend fun deleteNote(item: TableNote) {
        daoManager.delete(item)
    }

    // update some note
    suspend fun updateNote(item: TableNote) {
        daoManager.update(item)
    }

    // add empty note
    suspend fun addEmptyNote(item: TableNote) {
        daoManager.add(item)
    }


    companion object {
        private const val DB_NAME = "db_notes"

    }

}