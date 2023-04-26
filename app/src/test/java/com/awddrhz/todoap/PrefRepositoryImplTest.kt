package com.awddrhz.todoap

import android.app.Application
import android.content.SharedPreferences
import com.awddrhz.todoap.data.sharedPrefs.PrefRepositoryImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class PrefRepositoryImplTest {
    private lateinit var subject: PrefRepositoryImpl
    private val sharedPreferencesMock: SharedPreferences = mock()
    private val editMock: SharedPreferences.Editor = mock()

    private val titleTest: String = "titleTest"
    private val descriptionTest: String = "descriptionTest"
    private val keyTest: String = "keyTest"
    private val valueTest: String = "valueTest"

    @Before
    fun setup(){
        subject = PrefRepositoryImpl(sharedPreferencesMock)
    }

    @Test
    fun getToDoItem_success(){
        `when`(sharedPreferencesMock.getString(
            PrefRepositoryImpl.PREFS_TITLE_KEY,
            PrefRepositoryImpl.PREFS_DEFAULT_VALUE
        )
        ).thenReturn(titleTest)
        `when`(sharedPreferencesMock.getString(
            PrefRepositoryImpl.PREFS_DESCRIPTION_KEY,
            PrefRepositoryImpl.PREFS_DEFAULT_VALUE
        )
        ).thenReturn(descriptionTest)
        val result = subject.getToDoItem()
        assertEquals("titleTest", result.title)
        assertEquals("descriptionTest", result.description)
    }

    @Test
    fun saveDataInPrefs_success(){
        `when`(sharedPreferencesMock.edit()).thenReturn(editMock)

        subject.saveDataInPrefs(keyTest, valueTest)
        verify(editMock).putString(keyTest, valueTest)
    }

}