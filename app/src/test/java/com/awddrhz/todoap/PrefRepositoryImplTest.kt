package com.awddrhz.todoap

import android.app.Application
import android.content.SharedPreferences
import com.awddrhz.todoap.data.sharedPrefs.PrefRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PrefRepositoryImplTest {
    private lateinit var subject: PrefRepositoryImpl
    private val application: SharedPreferences = mock()


    @Before
    fun setup(){
        subject = PrefRepositoryImpl(application)
    }

    @Test
    fun getToDoItem_success(){
        subject.getToDoItem()
    }

}