package com.awddrhz.todoap.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.awddrhz.todoap.data.PrefManager

/**
 * Use to manage work with SharedPreferences
 */
class PrefManagerImpl(application: Application) : PrefManager{

  private val sharedPref : SharedPreferences = application.getSharedPreferences("preferences",Context.MODE_PRIVATE)

    override fun getToDoItem(): ToDoItem {
        val title = sharedPref.getString("titleKey", "") ?: ""
        val description = sharedPref.getString("descriptionKey", "") ?: ""
        return ToDoItem(0, title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

}