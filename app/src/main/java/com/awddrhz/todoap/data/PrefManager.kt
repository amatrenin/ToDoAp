package com.awddrhz.todoap.data

import com.awddrhz.todoap.data.ToDoItem

interface PrefManager {
    fun getToDoItem() : ToDoItem
    fun saveDataInPrefs(key: String, value: String)
}