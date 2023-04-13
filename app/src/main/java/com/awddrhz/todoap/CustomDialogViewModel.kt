package com.awddrhz.todoap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awddrhz.todoap.data.PrefManagerImpl
import com.awddrhz.todoap.data.ToDoItem
import com.awddrhz.todoap.data.PrefManager

class CustomDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val prefManager: PrefManager = PrefManagerImpl(application)

    private val todoItem: MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult: LiveData<ToDoItem> = todoItem

    fun getToDoItemFromPrefs() {
        val result = prefManager.getToDoItem()
        todoItem.postValue(result)
    }

    fun saveDataInPrefs(key: String, value: String) {
        prefManager.saveDataInPrefs(key, value)
    }


}