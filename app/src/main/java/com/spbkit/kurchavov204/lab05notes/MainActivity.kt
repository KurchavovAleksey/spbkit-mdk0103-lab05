package com.spbkit.kurchavov204.lab05notes

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {  // Курчавов Алексей 204 группа СПБКИТ
    private val notesList = mutableListOf<Note>()
    private val recyclerViewAdapter = CustomRecyclerAdapter(notesList)  // Курчавов Алексей 204 группа СПБКИТ

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {  // Курчавов Алексей 204 группа СПБКИТ
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
          // Курчавов Алексей 204 группа СПБКИТ
        val resultLauncherNewNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentReceived: Intent? = result.data  // Курчавов Алексей 204 группа СПБКИТ
                val note = intentReceived?.getSerializableExtra(IntentContentNames.NoteReturn.name) as Note
                notesList.add(note)
                recyclerViewAdapter.notifyDataSetChanged()
            }  // Курчавов Алексей 204 группа СПБКИТ
        }


        findViewById<Button>(R.id.buttonNew).setOnClickListener {
            val notesIntent = Intent(this, NoteActivity::class.java)  // Курчавов Алексей 204 группа СПБКИТ
            resultLauncherNewNote.launch(notesIntent)
        }

        findViewById<Button>(R.id.buttonDelete).setOnClickListener{
            recyclerViewAdapter.selectedPos?.let { selectedPos ->
                val builder = AlertDialog.Builder(this)  // Курчавов Алексей 204 группа СПБКИТ
                builder.create()
                builder.setTitle(getString(R.string.delete_dialog))
                builder.setCancelable(true)
                builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                builder.setPositiveButton(getString(R.string.button_delete)) { _, _ ->
                    notesList.removeAt(selectedPos)  // Курчавов Алексей 204 группа СПБКИТ
                    recyclerViewAdapter.resetSelected()
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                builder.show()
            }  // Курчавов Алексей 204 группа СПБКИТ
        }

        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            recyclerViewAdapter.selectedPos?.let{
                val noteToEdit = notesList[it]
                val noteEditIntent = Intent(this, NoteActivity::class.java)  // Курчавов Алексей 204 группа СПБКИТ
                noteEditIntent.putExtra(
                    IntentContentNames.NoteEditPassToEdit.name,
                    noteToEdit
                )
                notesList.removeAt(it)
                recyclerViewAdapter.resetSelected()  // Курчавов Алексей 204 группа СПБКИТ
                resultLauncherNewNote.launch(noteEditIntent)
            }

        }
        val recyclerView = findViewById<RecyclerView>(R.id.notesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)  // Курчавов Алексей 204 группа СПБКИТ
        recyclerView.adapter = recyclerViewAdapter

    }
}

class CustomRecyclerAdapter(private val notes: MutableList<Note>) : RecyclerView
.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    var selectedPos: Int? = null
    var selectedItem: View? = null  // Курчавов Алексей 204 группа СПБКИТ

    class MyViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(itemView),  // Курчавов Алексей 204 группа СПБКИТ
        View.OnClickListener {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)

        init {
            itemView.setOnClickListener(this)  // Курчавов Алексей 204 группа СПБКИТ
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }  // Курчавов Алексей 204 группа СПБКИТ

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)  // Курчавов Алексей 204 группа СПБКИТ
            .inflate(R.layout.recyclerview_item, parent, false)

        return MyViewHolder(itemView, onItemClicked = {
            when (selectedPos) {
                null -> {  // Курчавов Алексей 204 группа СПБКИТ
                    itemView.setBackgroundColor(Color.GREEN)
                    selectedPos = it
                    selectedItem = itemView
                }  // Курчавов Алексей 204 группа СПБКИТ
                it -> {
                    itemView.setBackgroundColor(Color.WHITE)
                    selectedPos = null
                    selectedItem = null
                }  // Курчавов Алексей 204 группа СПБКИТ
                else -> {
                    selectedItem?.setBackgroundColor(Color.WHITE)
                    selectedPos = it
                    selectedItem = itemView
                    itemView.setBackgroundColor(Color.GREEN)  // Курчавов Алексей 204 группа СПБКИТ
                }
            }
        })
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = notes[position].name  // Курчавов Алексей 204 группа СПБКИТ
        holder.smallTextView.text = notes[position].content.take(10)
        holder.itemView.setBackgroundColor(Color.WHITE)
    }
      // Курчавов Алексей 204 группа СПБКИТ
    override fun getItemCount() = notes.size

    fun resetSelected() {
        selectedItem?.setBackgroundColor(Color.WHITE)  // Курчавов Алексей 204 группа СПБКИТ
        selectedItem = null
        selectedPos = null
    }
}  // Курчавов Алексей 204 группа СПБКИТ