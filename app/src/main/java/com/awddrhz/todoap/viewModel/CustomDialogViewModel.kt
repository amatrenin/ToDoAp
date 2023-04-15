package com.awddrhz.todoap.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awddrhz.todoap.data.sharedPrefs.PrefManagerImpl
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.data.sharedPrefs.PrefManager

class CustomDialogViewModel(application: Application) : AndroidViewModel(application) {
    private val prefManager: PrefManager = PrefManagerImpl(application)

    private val todoItem: MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult: LiveData<ToDoItem> = todoItem

    /**
    * Provides preferences values for ToDo item
     */
    fun getToDoItemFromPrefs() {
        val result = prefManager.getToDoItem()
        todoItem.postValue(result)
    }

    /**
     * Save data in shared preferences manager
     * @param key - provide prefs information to save data
     * @param value - provide data that need to be saved in prefs
     */
    fun saveDataInPrefs(key: String, value: String) {
        prefManager.saveDataInPrefs(key, value)
    }
}