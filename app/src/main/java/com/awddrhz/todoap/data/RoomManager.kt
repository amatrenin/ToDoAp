package com.awddrhz.todoap.data

import com.awddrhz.todoap.data.ToDoItem

interface RoomManager {
    fun getAllItem(): List<ToDoItem>
    fun addItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
}