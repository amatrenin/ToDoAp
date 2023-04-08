package com.awddrhz.todoap.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.awddrhz.todoap.MainActivity
import com.awddrhz.todoap.R
import com.awddrhz.todoap.data.ToDoItem


class CustomDialog(
    private var activity: MainActivity,
    private val isNewItem: Boolean,
    private val item: ToDoItem?
) : Dialog(activity), View.OnClickListener {

    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText
    private lateinit var dialog_label: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_template)

        dialogSizeControl()
        itemView()

        if (isNewItem) {
            createNewItem()

        } else {
            updateItem()
        }


    }

//    private fun deleteItem() {
//        buttonDeleteItem()
//    }

    private fun updateItem() {
        dialog_label.text = "Update Item"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)

    }

    private fun createNewItem() {
//        val inputTitle = inputFieldTitle.text.toString()
//        val inputDescription = inputFieldDescription.text.toString()
//        activity.addItem(ToDoItem(0, inputTitle, inputDescription))

//        dismiss()

        Log.d("isBoolean", "isBoolean -> createNewItem")
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val titleFromPrefs = sharedPref?.getString("titleKey", "")
        val descriptionFromPrefs = sharedPref?.getString("descriptionKey", "")
        inputFieldTitle.setText(titleFromPrefs)
        inputFieldDescription.setText(descriptionFromPrefs)

    }

    private fun itemView() {
        inputFieldTitle = findViewById(R.id.dialog_item_title)
        inputFieldDescription = findViewById(R.id.dialog_item_description)
        okButton = findViewById<Button>(R.id.dialogOkButton)
        cancelButton = findViewById<Button>(R.id.dialogCancelButton)
        dialog_label = findViewById<Button>(R.id.dialog_label)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
        }

    private fun dialogSizeControl() {
        val p = WindowManager.LayoutParams()
        p.copyFrom(this.window?.attributes)
        p.width = WindowManager.LayoutParams.MATCH_PARENT
        p.height = WindowManager.LayoutParams.WRAP_CONTENT
        p.gravity = Gravity.CENTER
        this?.window?.attributes = p    }

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

//    private fun buttonDeleteItem() {
//        val inputTitle = inputFieldTitle.text.toString()
//        val inputDescription = inputFieldDescription.text.toString()
//        activity.deleteItem(ToDoItem(0, inputTitle, inputDescription))
//        dismiss()
//    }

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
        activity.addItem(ToDoItem(0, inputTitle, inputDescription))
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
                dismiss()
    }

    override fun onStop() {
        super.onStop()

        if (isNewItem) updateSharedPred() else {}

    }

    private fun updateSharedPred() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            val inputTitle = inputFieldTitle.text.toString()
            val inputDescription = inputFieldDescription.text.toString()
            putString("titleKey", inputTitle)
            putString("descriptionKey", inputDescription)
            apply()
        }    }

}