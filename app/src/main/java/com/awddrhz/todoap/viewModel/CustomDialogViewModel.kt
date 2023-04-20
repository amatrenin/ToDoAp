package com.awddrhz.todoap.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.data.sharedPrefs.PrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomDialogViewModel @Inject constructor(private val prefRepository: PrefRepository) : ViewModel() {

    private val todoItem: MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult: LiveData<ToDoItem> = todoItem

    /**
    * Provides preferences values for ToDo item
     */
    fun getToDoItemFromPrefs() {
        val result = prefRepository.getToDoItem()
        todoItem.postValue(result)
    }

    /**
     * Save data in shared preferences Repository
     * @param key - provide prefs information to save data
     * @param value - provide data that need to be saved in prefs
     */
    fun saveDataInPrefs(key: String, value: String) {
        prefRepository.saveDataInPrefs(key, value)
    }
}