package com.awddrhz.todoap.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awddrhz.todoap.data.ToDoItem

@Database(entities = [ToDoItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}