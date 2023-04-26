package com.awddrhz.todoap.data.sharedPrefs

import android.content.SharedPreferences
import com.awddrhz.todoap.data.room.ToDoItem
import javax.inject.Inject

/**
 * Repository that handles logic with shared preferences
 */
class PrefRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PrefRepository {

    override fun getToDoItem(): ToDoItem {
        val title =
            sharedPreferences.getString(PREFS_TITLE_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        val description = sharedPreferences.getString(PREFS_DESCRIPTION_KEY, PREFS_DEFAULT_VALUE)
            ?: PREFS_DEFAULT_VALUE
        return ToDoItem(0, title, description)
        return ToDoItem(0, "title", "description")
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with(sharedPreferences.edit()) {
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