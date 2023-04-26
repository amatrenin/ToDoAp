package com.awddrhz.todoap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.awddrhz.todoap.data.room.ToDoItem
import com.awddrhz.todoap.data.sharedPrefs.PrefRepository
import com.awddrhz.todoap.viewModel.CustomDialogViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CustomDialogViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: CustomDialogViewModel
    private val prefRepository: PrefRepository = mock()

    private val toDoItemMock: ToDoItem = ToDoItem(0, "testTitle", "testDescription")
    private val keyTestValue: String = "keyTest"
    private val valueTestValue: String = "value"

    @Before
    fun setup() {
        subject = CustomDialogViewModel(prefRepository)
    }

    @Test
    fun getToDoItemFromPrefs_success() {
        `when`(prefRepository.getToDoItem()).thenReturn(toDoItemMock)
        subject.getToDoItemFromPrefs()
        val expectedResult = subject.todoItemResult.value?.title
        assertEquals("testTitle", expectedResult)
    }

    @Test
    fun saveDataInPrefs_success() {
        subject.saveDataInPrefs(keyTestValue, valueTestValue)
        verify(prefRepository).saveDataInPrefs(keyTestValue, valueTestValue)
    }
}


