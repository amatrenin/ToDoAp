package com.awddrhz.todoap.data.room

import android.content.Context
import androidx.room.Room


/**
 * Manager that handles logic with room data base
*/
class RoomManagerImpl(private val context: Context) : RoomManager {

    private var db = Room.databaseBuilder(
    context,
    AppDatabase::class.java, DATABASE_NAME
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

     companion object {
         private const val DATABASE_NAME = "database-name"
     }
}