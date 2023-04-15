package com.awddrhz.todoap.data.room

interface RoomManager {

    /**
     * Return todo item from room data base
     */
    fun getAllItem(): List<ToDoItem>

    /**
     * add todo item from room data base
     */
    fun insertItem(item: ToDoItem)

    /**
     * update todo item in room data base
     */
    fun updateItem(item: ToDoItem)

    /**
     * delete todo item from room data base
     */
    fun deleteItem(item: ToDoItem)
}