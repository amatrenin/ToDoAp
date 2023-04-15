package com.awddrhz.todoap.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awddrhz.todoap.room.ToDoDao

@Database(entities = [ToDoItem::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}