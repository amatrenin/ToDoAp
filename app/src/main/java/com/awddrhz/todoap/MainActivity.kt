package com.awddrhz.todoap

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.awddrhz.todoap.adapter.CustomAdapter
import com.awddrhz.todoap.data.ToDoItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ItemOnClick {

    private val mMainViewModel: MainViewModel by viewModels()

    private lateinit var bContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerview: RecyclerView

    private lateinit var data : List<ToDoItem>

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.main_fab)
        bContainer = findViewById(R.id.main_no_items_container)
        recyclerview = findViewById<RecyclerView>(R.id.main_rcView)

        fab.setOnClickListener {
            val dialogFragment = CustomDialog(this, true, null)
            dialogFragment.show(supportFragmentManager, "Custom dialog")
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
//        val data = ArrayList<ToDoItem>()

        adapter = CustomAdapter(mutableListOf(), this)

        // This will pass the ArrayList to our Adapter
//        adapter = CustomAdapter(data)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        mMainViewModel.getAllItem()
        mMainViewModel.todoItemResult.observe(this) {
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
        }

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.cancel_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
//        val backgroundColor = Color.parseColor("#f44336")
//        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode (PorterDuff.Mode.CLEAR) }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }


            // Let's draw our delete view
            override fun onChildDraw(canvas: Canvas,
                                     recyclerView: RecyclerView,
                                     viewHolder: ViewHolder,
                                     dX: Float, dY: Float,
                                     actionState: Int,
                                     isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                // Draw the red delete background
                background.color = Color.RED
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                // Calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(canvas)

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY,
                    actionState,
                    isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedTodoItem: ToDoItem =
                    data[viewHolder.adapterPosition]

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                data.toMutableList().removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                mMainViewModel.deleteItem(deletedTodoItem)
//                    .toDoDao().deleteItem(deletedCourse)
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(recyclerview, "Deleted " + deletedTodoItem.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            data.toMutableList().add(position, deletedTodoItem)
                            insertItem(deletedTodoItem)
//                            db.toDoDao().insertItem(deletedCourse)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()

            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerview)

    }


    private fun screenDataValidation(it: List<ToDoItem>?) {
        if (it != null) {
            if (it.isEmpty()) {
                bContainer.visibility = View.VISIBLE
                recyclerview.visibility = View.INVISIBLE
            } else {
                bContainer.visibility = View.INVISIBLE
                recyclerview.visibility = View.VISIBLE
            }
        }
    }


//    @SuppressLint("SuspiciousIndentation")
    fun insertItem(item: ToDoItem) {
        bContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        mMainViewModel.insertItem(item)
    }

    fun updateItem(item: ToDoItem) {
        bContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        mMainViewModel.updateItem(item)
    }

    fun deleteItem(item: ToDoItem) {
        bContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        mMainViewModel.deleteItem(item)
    }

    override fun onClikedItem(item: ToDoItem) {
        val dialogFragment = CustomDialog(this, false, item)
        dialogFragment.show(supportFragmentManager, "Custom dialog")
    }

  }