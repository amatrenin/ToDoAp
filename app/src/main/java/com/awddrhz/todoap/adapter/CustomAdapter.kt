package com.awddrhz.todoap.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.awddrhz.todoap.ItemOnClick
import com.awddrhz.todoap.R
import com.awddrhz.todoap.data.ToDoItem

class CustomAdapter(private var mList: MutableList<ToDoItem>, private val itemOnClick: ItemOnClick) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

       holder.title.text = ItemsViewModel.title
       holder.description.text = ItemsViewModel.description
        holder.container.setOnClickListener {
            itemOnClick.onClikedItem(mList[position])
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.item_rc_title)
        val description: TextView = itemView.findViewById(R.id.item_rc_description)
        val container: ConstraintLayout = itemView.findViewById(R.id.item_rc_conteiner)
    }

    fun updateList(updatedList: List<ToDoItem>) {
        mList = updatedList.toMutableList()
        notifyDataSetChanged()
    }
}