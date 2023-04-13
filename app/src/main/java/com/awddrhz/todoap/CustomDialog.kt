package com.awddrhz.todoap

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.awddrhz.todoap.data.ToDoItem


class CustomDialog(
    private var activity: MainActivity,
    private val isNewItem: Boolean,
    private val item: ToDoItem?
) : DialogFragment(), View.OnClickListener {

    private val mCustomDialogViewModel: CustomDialogViewModel by activityViewModels()


    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText
    private lateinit var dialog_label: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_template, container, false)


        itemView(view)

        if (isNewItem) {
            mCustomDialogViewModel.getToDoItemFromPrefs()

        } else {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        dialog_label.text = "Update Item"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
    }

    override fun onResume() {
        super.onResume()
        dialogSizeControl()
        if (isNewItem){
        mCustomDialogViewModel.todoItemResult.observe(this) {
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
        dialog_label = view.findViewById<Button>(R.id.dialog_label)
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
                cancelButtonCliker()
            }
            else -> {
            }
        }
    }

    private fun cancelButtonCliker() {
        dismiss()
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
            ?.let { activity.updateItem(it) }
        dismiss()
    }

    private fun buttonOkBeenClicked() {
        val inputTitle = inputFieldTitle.text.toString()
        val inputDescription = inputFieldDescription.text.toString()
        activity.insertItem(ToDoItem(0, inputTitle, inputDescription))
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
                dismiss()
    }

    override fun onStop() {
        super.onStop()

        if (isNewItem) {
            val inputTitle = inputFieldTitle.text.toString()
            val inputDescription = inputFieldDescription.text.toString()
            mCustomDialogViewModel.saveDataInPrefs("titleKey", inputTitle)
            mCustomDialogViewModel.saveDataInPrefs("descriptionKey", inputDescription)
        }

    }
}