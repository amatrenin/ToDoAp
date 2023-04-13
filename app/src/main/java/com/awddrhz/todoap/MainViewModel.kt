package com.awddrhz.todoap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awddrhz.todoap.data.RoomManagerImpl
import com.awddrhz.todoap.data.ToDoItem
import com.awddrhz.todoap.data.RoomManager

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val roomManager: RoomManager = RoomManagerImpl(application)

    private val todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemResult: LiveData<List<ToDoItem>> = todoItemList

    fun getAllItem() {
        val result = roomManager.getAllItem()
        todoItemList.postValue(result)
    }

    fun insertItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.plus(item))
            roomManager.insertItem(item)
        }
    }

    fun updateItem(item: ToDoItem) {
        roomManager.updateItem(item)
    }

    fun deleteItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomManager.deleteItem(item)
        }

    }
}