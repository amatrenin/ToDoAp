package com.awddrhz.todoap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.awddrhz.todoap.data.room.RoomRepositoryImpl
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.room.ToDoDao
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class RoomRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: RoomRepositoryImpl
    private val todoDaoMock: ToDoDao = mock()

    private val mockItemOne: ToDoItem = ToDoItem(0, "testTitleOne", "testDescriptionOne")
    private val mockItemTwo: ToDoItem = ToDoItem(1, "testTitleTwo", "testDescriptionTwo")

    private val mockList: List<ToDoItem> = listOf(
        mockItemOne,
        mockItemTwo
    )

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(todoDaoMock)
    }

    @Test
    fun getAllItem_success(){
        `when`(todoDaoMock.getAllItems()).thenReturn(mockList)
        subject.getAllItem()
        val result = subject.getAllItem()
        assertEquals(2, result.size)
    }

    @Test
    fun insertItem_success() {
        subject.insertItem(mockItemOne)
        verify(todoDaoMock).insertItem(mockItemOne)
    }

    @Test
    fun updateItem_success() {
        subject.updateItem(mockItemOne)
        verify(todoDaoMock).updateItem(mockItemOne)
    }

    @Test
    fun deleteItem_success() {
        subject.deleteItem(mockItemOne)
        verify(todoDaoMock).deleteItem(mockItemOne)
    }
}