package com.example.to_do

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/**
 * Bridge that tells the recyclerview how to display the data we give it
 * **/

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: onLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>()  {

    interface onLongClickListener {
        fun onItemLongClicked(position: Int) // let us define what happens when an item is clicked
    }

    // Usually involves inflating a layout from XML and returning the holder
    // Will inflate a specific layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context) // used to inflate layout for each item
        // Inflate the custom layout, simple_list_item_1 is a custom layout provided by android
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Takes whatever is in the listOfItems and use that to populate the layout inside view holder

        // Get the data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in our layout view
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }
}
/**
 * When implementing long click listener
 * 1. Set an on click listener for every single item (setOnClickListener)
 * 2. Allow taskAdapter take a longClickListener + create and interface for OnLongClickListener with the specific onItemLongClicked
 * 3. In our taskItemAdapter also pass onLongClickListener
 * **/