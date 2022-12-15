package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firestore = FirebaseFirestore.getInstance()
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }

        firestore.collection("notes").orderBy("created_at", Query.Direction.DESCENDING).get().addOnSuccessListener { doc ->
            val note_list = ArrayList<Note>()
            for (d in doc){
                note_list.add(Note("${d.data["judul"]}", "${d.data["isi"]}", "${d.data["created_at"]}", "${d.id}"))
            }
            val rcy_note = findViewById<RecyclerView>(R.id.viewRec)
            rcy_note.layoutManager = LinearLayoutManager(this)
            rcy_note.adapter = NoteAdapter(note_list)
        }.addOnFailureListener {
            Log.d("test", "Pengambilan data gagal")
        }

    }
}