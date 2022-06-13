package com.example.testapplication.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface NoteDao {

    @Query("SELECT * FROM table_notes ORDER BY `date` asc")
    fun getAll(): LiveData<List<TableNote>>

    @Insert
    suspend fun add(item: TableNote)

    @Update
    suspend fun update(item: TableNote)

    @Delete
    suspend fun delete(item: TableNote)

}