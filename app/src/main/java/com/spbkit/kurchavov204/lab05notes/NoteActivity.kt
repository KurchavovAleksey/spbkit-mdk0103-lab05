package com.spbkit.kurchavov204.lab05notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val intentReceived: Intent? = intent
        val receivedNote: Note? = intentReceived?.getSerializableExtra(IntentContentNames.NoteEditPassToEdit.name) as Note?
        receivedNote?.let {
            findViewById<EditText>(R.id.editTextTitle).setText(it.name)
            findViewById<EditText>(R.id.contentEditText).setText(it.content)
        }

        val saveButton = findViewById<Button>(R.id.buttonSave)

        saveButton.setOnClickListener {
            val returnNoteIntent = Intent()
            returnNoteIntent.putExtra(
                IntentContentNames.NoteReturn.name,
                Note(
                    findViewById<EditText>(R.id.editTextTitle).text.toString(),
                    findViewById<EditText>(R.id.contentEditText).text.toString()
                )
            )

            setResult(RESULT_OK, returnNoteIntent)
            finish()
        }

        findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            finish()
        }
    }
}