package com.awddrhz.todoap.data.sharedPrefs

import com.awddrhz.todoap.data.room.ToDoItem

interface PrefRepository {

    /**
     * Return todo item from prefs
     */
    fun getToDoItem() : ToDoItem

    /**
     * Saving data in prefs
     */
    fun saveDataInPrefs(key: String, value: String)
}