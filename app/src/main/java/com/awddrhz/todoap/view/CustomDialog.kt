package com.awddrhz.todoap.view

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.awddrhz.todoap.viewModel.CustomDialogViewModel
import com.awddrhz.todoap.viewModel.MainViewModel
import com.awddrhz.todoap.R
import com.awddrhz.todoap.data.sharedPrefs.PrefManagerImpl.Companion.PREFS_DESCRIPTION_KEY
import com.awddrhz.todoap.data.sharedPrefs.PrefManagerImpl.Companion.PREFS_TITLE_KEY
import com.awddrhz.todoap.data.room.ToDoItem


class CustomDialog(
    private val isNewItem: Boolean,
    private val item: ToDoItem?
) : DialogFragment(), View.OnClickListener {

    private val customDialogViewModel: CustomDialogViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText
    private lateinit var dialogLabel: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_template, container, false)

        itemView(view)

        if (isNewItem) {
            customDialogViewModel.getToDoItemFromPrefs()
        } else {
            updateItem()
        }
        return view
    }

    private fun updateItem() {
        dialogLabel.text = getString(R.string.update_item)
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
    }

    override fun onResume() {
        super.onResume()
        dialogSizeControl()
        observers()
    }

    private fun observers() {
        customDialogViewModel.todoItemResult.observe(this) {
            if (isNewItem){
                inputFieldTitle.setText(it.title)
                inputFieldDescription.setText(it.description)
            }
        }
    }

    private fun itemView(view: View) {
        inputFieldTitle = view.findViewById(R.id.dialog_item_title)
        inputFieldDescription = view.findViewById(R.id.dialog_item_description)
        okButton = view.findViewById<Button>(R.id.dialogOkButton)
        cancelButton = view.findViewById<Button>(R.id.dialogCancelButton)
        dialogLabel = view.findViewById<Button>(R.id.dialog_label)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
        }

    private fun dialogSizeControl() {
      val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height = ActionBar.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as LayoutParams
       }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialogOkButton -> {
               okButtonCliker()
            }
            R.id.dialogCancelButton -> {
                dismiss()
            }
            else -> {
            }
        }
    }

    private fun okButtonCliker() {
            if (isNewItem) {
                buttonOkBeenClicked()
            }
            else {
                buttonUpdateBeenClicked()
            }
    }

    private fun buttonUpdateBeenClicked() {
        val inputTitle = inputFieldTitle.text.toString()
        val inputDescription = inputFieldDescription.text.toString()
        item?.id?.let { ToDoItem(it, inputTitle, inputDescription) }
            ?.let { mainViewModel.updateItem(it) }
        dismiss()
    }

    private fun buttonOkBeenClicked() {
        val inputTitle = inputFieldTitle.text.toString()
        val inputDescription = inputFieldDescription.text.toString()
        if (inputTitle.isEmpty()) {
            Toast.makeText(activity, "Empty, enter data!!", Toast.LENGTH_SHORT).show()
        } else {
            mainViewModel.insertItem(ToDoItem(0, inputTitle, inputDescription))
            inputFieldTitle.text.clear()
            inputFieldDescription.text.clear()
            dismiss()
        }
    }

    override fun onStop() {
        super.onStop()

        if (isNewItem) {
            val inputTitle = inputFieldTitle.text.toString()
            val inputDescription = inputFieldDescription.text.toString()
            customDialogViewModel.saveDataInPrefs(PREFS_TITLE_KEY, inputTitle)
            customDialogViewModel.saveDataInPrefs(PREFS_DESCRIPTION_KEY, inputDescription)
        }
    }
}