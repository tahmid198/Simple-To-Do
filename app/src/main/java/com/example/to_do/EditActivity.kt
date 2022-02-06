package com.example.to_do

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {

    private fun onSubmit(updatedTask: String, position: Int) {
        // Prepare data intent
        val data = Intent()
        // Pass relevant data back as a result
        data.putExtra("todo", updatedTask)
        data.putExtra("position", position)
        // Activity finished ok, return the data
        setResult(RESULT_OK, data) // set result code and bundle data for response
        finish() // closes the activity, pass data to parent
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Get the updated text form edit field from edit field
        val inputTExtField = findViewById<EditText>(R.id.editTaskTextField)

        // Set the text in the update field to be the current to-do value
        inputTExtField.setText(intent.getStringExtra("todo"))

        // Set a reference to the edit button and then set an onClickListener
        findViewById<Button>(R.id.editButton).setOnClickListener {
            //Get text form user @id/editTaskTextField
            val updatedTask = inputTExtField.text.toString()

            // Return to main screen
            onSubmit(updatedTask, intent.getIntExtra("position", 0))
        }
    }
}

// https://guides.codepath.org/android/Using-Intents-to-Create-Flows
