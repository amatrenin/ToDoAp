package com.awddrhz.todoap.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awddrhz.todoap.data.room.RoomManagerImpl
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.data.room.RoomManager

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val roomManager: RoomManager = RoomManagerImpl(application)

    private val todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemResult: LiveData<List<ToDoItem>> = todoItemList

    /**
     * Provides all data from room
     */
    fun getAllItem() {
        val result = roomManager.getAllItem()
        todoItemList.postValue(result)
    }

    /**
     * Insert Item in room data base
     * @param item - provides item that need to be insert in room data base
     */
    fun insertItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.plus(item))
            roomManager.insertItem(item)
        }
    }

    /**
     * Updated existing item in room database
     * @param item - provides item that need to be updated in room data base
     */
    fun updateItem(item: ToDoItem) {
        val foundIndex = todoItemList.value?.indexOfFirst { it.id == item.id } //Search for needed item
        foundIndex?.let {
            val list = todoItemList.value?.toMutableList()
            list?.set(it, item)
            todoItemList.value = list!!
        }
        roomManager.updateItem(item)
    }

    /**
     * Delete existing item from room database
     * @param item - provides item that need to be deleted from room data base
     */
    fun deleteItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomManager.deleteItem(item)
        }
    }
}