package com.awddrhz.todoap.view

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.awddrhz.todoap.data.room.ItemOnClick
import com.awddrhz.todoap.viewModel.MainViewModel
import com.awddrhz.todoap.R

import com.awddrhz.todoap.adapter.CustomAdapter
import com.awddrhz.todoap.data.room.ToDoItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ItemOnClick {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var stubContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var data : List<ToDoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        swipeImplementation()
        observers()

        fab.setOnClickListener {
            val dialogFragment = CustomDialog( true, null)
            dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
        }
        mainViewModel.getAllItem()
    }

    private fun observers() {
        mainViewModel.todoItemResult.observe(this) {
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
        }
    }

    private fun swipeImplementation() {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.cancel_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Let's draw our delete view
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onChildDraw(canvas: Canvas,
                                     recyclerView: RecyclerView,
                                     viewHolder: ViewHolder,
                                     dX: Float, dY: Float,
                                     actionState: Int,
                                     isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                // Draw the red delete background
                background.color = getColor(R.color.delete)
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

                val position = viewHolder.adapterPosition

                data.toMutableList().removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                mainViewModel.deleteItem(deletedTodoItem)
                // below line is to display our snackbar with action.
                Snackbar.make(recyclerView, "Deleted " + deletedTodoItem.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        getString(R.string.undo),
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            data.toMutableList().add(position, deletedTodoItem)
                            mainViewModel.insertItem(deletedTodoItem)
                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView)
    }

    private fun screenDataValidation(list: List<ToDoItem>) {
            if (list.isEmpty()) {
                setupStub(showStub = true, showRecycler = false)
            } else {
                setupStub(showStub = false, showRecycler = true)
        }
    }

    private fun setupStub(showStub: Boolean, showRecycler: Boolean) {
         stubContainer.isVisible = showStub
         recyclerView.isVisible = showRecycler
    }

    private fun initView() {
        fab = findViewById(R.id.main_fab)
        stubContainer = findViewById(R.id.main_no_items_container)
        recyclerView = findViewById<RecyclerView>(R.id.main_rcView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    override fun onClikedItem(item: ToDoItem) {
        val dialogFragment = CustomDialog(false, item)
        dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
    }
  }

