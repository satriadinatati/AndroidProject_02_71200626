package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class EditNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val firestore = FirebaseFirestore.getInstance()

        val inputJudul = findViewById<EditText>(R.id.inputJudul)
        val inputIsi = findViewById<EditText>(R.id.inputIsi)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val doc_id = intent.getStringExtra("doc_id").toString()
        var created_at: String? = null

        firestore.collection("notes").document(doc_id).get().addOnSuccessListener { doc ->
            Log.d("test", "data: ${doc.data}")
            inputJudul.setText(doc.data?.get("judul").toString())
            inputIsi.setText(doc.data?.get("isi").toString())
            created_at = doc.data?.get("created_at").toString()
        }.addOnFailureListener {
            Log.d("test", "Pengambilan data gagal")
        }

        btnSave.setOnClickListener {
            firestore.collection("notes").document(doc_id).update("isi", inputIsi.text.toString())
            firestore.collection("notes").document(doc_id).update("judul", inputJudul.text.toString())
            Toast.makeText(this, "berhasil di simpan", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}