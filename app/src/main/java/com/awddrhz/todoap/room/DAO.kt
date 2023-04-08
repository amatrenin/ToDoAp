package com.awddrhz.todoap.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awddrhz.todoap.data.ToDoItem


@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoitem")
    fun getAllItems(): LiveData<List<ToDoItem>>

    @Insert
    fun insertItem(toDoItem: ToDoItem)

    @Delete
    fun deleteItem(toDoItem: ToDoItem)

    @Update
    fun updateItem(toDoItem: ToDoItem)
}
