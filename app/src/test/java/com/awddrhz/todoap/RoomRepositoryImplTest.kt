package com.awddrhz.todoap

import android.app.Application
import com.awddrhz.todoap.data.room.RoomRepositoryImpl
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.room.ToDoDao
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class RoomRepositoryImplTest {
    private lateinit var subject: RoomRepositoryImpl
    private val application: ToDoDao = mock()

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(application)
    }
    @Test
    fun getAllItem(){
        subject.getAllItem()
    }
}