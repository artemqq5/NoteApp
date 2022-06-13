package com.example.testapplication.viewModel



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.database.RepositoryDB
import com.example.testapplication.database.TableNote
import kotlinx.coroutines.*


class ViewModelManage : ViewModel() {

    private val repository by lazy {
        RepositoryDB()
    }

    val liveDataNotes: LiveData<List<TableNote>> =  repository.getAllNotes()


    fun addData(item: TableNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEmptyNote(item)
        }
    }

    fun deleteData(item: TableNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(item)
        }
    }

    fun updateData(item: TableNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(item)
        }
    }

}