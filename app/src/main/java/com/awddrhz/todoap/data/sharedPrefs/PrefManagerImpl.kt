package com.awddrhz.todoap.data.sharedPrefs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.awddrhz.todoap.data.room.ToDoItem

/**
 * Manager that handles logic with shared preferences
 */
class PrefManagerImpl(application: Application) : PrefManager {

  private val sharedPref : SharedPreferences = application.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    override fun getToDoItem(): ToDoItem {
        val title = sharedPref.getString(PREFS_TITLE_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        val description = sharedPref.getString(PREFS_DESCRIPTION_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        return ToDoItem(0, title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    companion object {
        const val PREFS_TITLE_KEY = "titleKey"
        const val PREFS_DESCRIPTION_KEY = "descriptionKey"
        const val PREFS_NAME = "preferences"
        const val PREFS_DEFAULT_VALUE = ""
    }
}