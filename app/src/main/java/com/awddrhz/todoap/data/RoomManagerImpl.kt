package com.awddrhz.todoap.data

import android.content.Context
import androidx.room.Room
import com.awddrhz.todoap.data.RoomManager
import com.awddrhz.todoap.room.AppDatabase


/**
 * Use to manage work with
 */
class RoomManagerImpl(private val context: Context) : RoomManager {

    private var db = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "database-name"
    )
    .allowMainThreadQueries()
    .fallbackToDestructiveMigration()
    .build()

    override fun getAllItem(): List<ToDoItem> {
       return db.toDoDao().getAllItems()
    }

    override fun insertItem(item: ToDoItem) {
        db.toDoDao().insertItem(item)
    }

    override fun updateItem(item: ToDoItem) {
        db.toDoDao().updateItem(item)
    }

    override fun deleteItem(item: ToDoItem) {
        db.toDoDao().deleteItem(item)
    }
}