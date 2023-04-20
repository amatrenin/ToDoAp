package com.awddrhz.todoap.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awddrhz.todoap.data.room.RoomRepository
import com.awddrhz.todoap.data.room.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    private val todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemResult: LiveData<List<ToDoItem>> = todoItemList

    /**
     * Provides all data from room
     */
    fun getAllItem() {
        val result = roomRepository.getAllItem()
        todoItemList.postValue(result)
    }

    /**
     * Insert Item in room data base
     * @param item - provides item that need to be insert in room data base
     */
    fun insertItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.plus(item))
            roomRepository.insertItem(item)
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
        roomRepository.updateItem(item)
    }

    /**
     * Delete existing item from room database
     * @param item - provides item that need to be deleted from room data base
     */
    fun deleteItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomRepository.deleteItem(item)
        }
    }
}