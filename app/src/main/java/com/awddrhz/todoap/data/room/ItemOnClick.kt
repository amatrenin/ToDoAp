package com.awddrhz.todoap.data.room

import com.awddrhz.todoap.data.room.ToDoItem

interface ItemOnClick {
   fun onClikedItem(item: ToDoItem)
}