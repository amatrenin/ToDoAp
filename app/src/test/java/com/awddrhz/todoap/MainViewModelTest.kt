package com.awddrhz.todoap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.awddrhz.todoap.data.room.RoomRepository
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.viewModel.MainViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: MainViewModel
    private val roomRepository: RoomRepository = mock()

    private val mockItemOne: ToDoItem = ToDoItem(0, "testTitleOne", "testDescriptionOne")
    private val mockItemTwo: ToDoItem = ToDoItem(1, "testTitleTwo", "testDescriptionTwo")
    private val mockItemThree: ToDoItem = ToDoItem(2, "testTitleThree", "testDescriptionThree")
    private val mockItemFour: ToDoItem = ToDoItem(0, "testTitleFour", "testDescriptionFour")

    private val mockList: List<ToDoItem> = listOf(
        mockItemOne,
        mockItemTwo
    )

    @Before
    fun setup(){
        subject = MainViewModel(roomRepository)
    }

    @Test
    fun getAllItem_success() {
        `when`(roomRepository.getAllItem()).thenReturn(mockList)
        subject.getAllItem()
        val expectedResult = subject.todoItemResult.value?.size
        val firstItem = subject.todoItemResult.value?.first()
        assertEquals(2, expectedResult)
        assertEquals("testTitleOne", firstItem?.title)
        assertEquals("testDescriptionOne", firstItem?.description)
    }

    @Test
    fun insertItem_success() {
        `when`(roomRepository.getAllItem()).thenReturn(mockList)
        subject.getAllItem()
        val expectedResult = subject.todoItemResult.value?.size
        assertEquals(2, expectedResult)

        subject.insertItem(mockItemThree)
        val expectedResultAfterInsert = subject.todoItemResult.value?.size
        assertEquals(3, expectedResultAfterInsert)
        val lastItem = subject.todoItemResult.value?.last()
        assertEquals(2, expectedResult)
        assertEquals("testTitleThree", lastItem?.title)
        assertEquals("testDescriptionThree", lastItem?.description)

    }

    @Test
    fun updateItem_success() {
        `when`(roomRepository.getAllItem()).thenReturn(mockList)
        subject.getAllItem()

        subject.updateItem(mockItemFour)
        val updatedItem = subject.todoItemResult.value?.first()
        assertEquals("testTitleFour", updatedItem?.title)
        assertEquals("testDescriptionFour", updatedItem?.description)
    }

    @Test
    fun deleteItem_success() {
        `when`(roomRepository.getAllItem()).thenReturn(mockList)
        subject.getAllItem()
        val expectedResult = subject.todoItemResult.value?.size
        assertEquals(2, expectedResult)

        subject.deleteItem(mockItemTwo)
        val deleteResult = subject.todoItemResult.value?.size
        assertEquals(1, deleteResult)
    }
}