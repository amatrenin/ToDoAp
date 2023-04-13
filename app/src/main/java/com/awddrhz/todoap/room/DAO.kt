package com.awddrhz.todoap.room

import androidx.room.*
import com.awddrhz.todoap.data.ToDoItem


@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoitem")
    fun getAllItems(): List<ToDoItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(toDoItem: ToDoItem)

    @Delete
    fun deleteItem(toDoItem: ToDoItem)

    @Update
    fun updateItem(toDoItem: ToDoItem)
}
