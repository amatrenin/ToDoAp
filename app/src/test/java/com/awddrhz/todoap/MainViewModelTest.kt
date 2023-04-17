package com.awddrhz.todoap

import android.app.Application
import com.awddrhz.todoap.data.room.RoomRepository
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.room.ToDoDao
import com.awddrhz.todoap.viewModel.MainViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class MainViewModelTest {
    private lateinit var subject: MainViewModel
    private val application: RoomRepository = mock()

    private val testList = ToDoItem(0, "testTitle", "testDescription")

    @Before
    fun setup(){
        subject = MainViewModel(application)
    }

    @Test
    fun insertItem_success() {
        subject.insertItem(testList)
    }
}