package com.example.note_app_firebase

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edNote :EditText
    private lateinit var btnSubmit :Button
    private lateinit var rvMain :RecyclerView
    private lateinit var rvAdapter : NoteAdapter

    lateinit var mainViewModel:MainViewModel
    lateinit var notes:ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes= arrayListOf()
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getNotes().observe(this,{
            notes->rvAdapter.update(notes)
        })

        edNote=findViewById(R.id.edNote)
        btnSubmit=findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            mainViewModel.addNote(Note("",edNote.text.toString()))
            edNote.text.clear()
            edNote.clearFocus()
        }
        rvMain=findViewById(R.id.rvMain)
        rvAdapter= NoteAdapter(this)
        rvMain.adapter=rvAdapter
        rvMain.layoutManager=LinearLayoutManager(this)
        mainViewModel.getData()
    }
    fun raiseDialog(id: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> mainViewModel.editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}