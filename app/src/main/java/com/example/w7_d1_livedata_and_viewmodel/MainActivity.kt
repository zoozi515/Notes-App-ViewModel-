package com.example.w7_d1_livedata_and_viewmodel

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w7_d1_livedata_and_viewmodel.adapter.NoteAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var rvNotes: RecyclerView
    private lateinit var rvAdapter: NoteAdapter
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button

    lateinit var mainViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        mainViewModel.getNotes().observe(this, {
                notes -> rvAdapter.updateRecycleView(notes)
        })

        editText = findViewById(R.id.messageEditText)
        submitBtn = findViewById(R.id.saveButton)
        submitBtn.setOnClickListener {
            mainViewModel.addNote(editText.text.toString())
            editText.text.clear()
            editText.clearFocus()
        }

        rvNotes = findViewById(R.id.recyclerView)
        rvAdapter = NoteAdapter(this)
        rvNotes.adapter = rvAdapter
        rvNotes.layoutManager = LinearLayoutManager(this)
    }
    fun raiseDialog(id: Int, isUpdate: Boolean){
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogLayout = LinearLayout(this)
        dialogLayout.orientation = LinearLayout.VERTICAL
        var title = ""

        if(isUpdate){
            title = "Update Note"
            val updatedNote = EditText(this)
            updatedNote.hint = "Enter new text"
            dialogLayout.addView(updatedNote)

            dialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save", DialogInterface.OnClickListener {
                        _, _ -> mainViewModel.editNote(id, updatedNote.text.toString())
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, _ -> dialog.cancel()
                })
        } else {
            title = "Delete Note"
            val deletNote = TextView(this)
            deletNote.text = "Are you sure that you want to delete this note? "
            dialogLayout.addView(deletNote)

            dialogBuilder
                .setCancelable(false)
                .setPositiveButton("Delete", DialogInterface.OnClickListener {
                        _, _ -> mainViewModel.deleteNote(id)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, _ -> dialog.cancel()
                })
        }

        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.setView(dialogLayout)
        alert.show()
    }
}