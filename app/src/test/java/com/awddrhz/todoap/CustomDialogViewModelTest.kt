package com.awddrhz.todoap

import android.app.Application
import com.awddrhz.todoap.data.sharedPrefs.PrefRepository
import com.awddrhz.todoap.viewModel.CustomDialogViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CustomDialogViewModelTest {
    private lateinit var subject : CustomDialogViewModel
    private val application : PrefRepository = mock()

    @Before
    fun setup() {
        subject = CustomDialogViewModel(application)
    }

    @Test
    fun getToDoItemFromPrefs_success() {
        subject.getToDoItemFromPrefs()
    }
}