package com.awddrhz.todoap

import com.awddrhz.todoap.data.ToDoItem

interface ItemOnClick {
   fun onClikedItem(item: ToDoItem)
}