package com.example.to_do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.FileUtils
import org.apache.commons.io.FileUtils;

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

// this is the file where we will be handling user interaction and any other needed logic
class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter // lateinit = eventually gonna be init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.onLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Modify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        // 1. Let's detect when the user clicks the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            // Code in here is going to be executed when user clicks on button
//            Log.i("Tahmid","User clicked on button")
//        }
//        listOfTasks.add("Do Laundry")
//        listOfTasks.add("Go for a Walk")

        loadItems()

        // Look up recycler view in the layout
        val  recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in teh sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerView to populate items, so they can know of each other
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Set up the button and input field, so that the user can enter a task and add it to the list
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted into @id/addTaskGField
            val userInputtedTask = inputTextField.text.toString() // will give us an Editable object so we need to convert to string

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapted that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)  // if we do not do this our list would not get updated

            // 3. Reset text field
            inputTextField.setText("")

            // call SaveItems
            saveItems()

        }
    }

    // Save the data that the user has inputted
    // Save data by writing and reading from a file

    // Create a method to get the file we need
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }

    // Load the items by reading every line in the data files
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems() {
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}