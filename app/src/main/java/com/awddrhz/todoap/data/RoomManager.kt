package com.awddrhz.todoap.data

interface RoomManager {
    fun getAllItem(): List<ToDoItem>
    fun insertItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
}