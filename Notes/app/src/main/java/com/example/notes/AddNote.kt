package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    var inputJudul: EditText? = null
    var inputIsi: EditText? = null
    var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        inputJudul = findViewById(R.id.inputJudul)
        inputIsi = findViewById(R.id.inputIsi)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit?.setOnClickListener {
            add()
        }
    }

    fun add(){
        val firestore = FirebaseFirestore.getInstance()
        var judul = inputJudul?.text.toString()
        var isi = inputIsi?.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val note = Note(judul, isi, currentDate)

        firestore.collection("notes").add(note).addOnSuccessListener {
            Log.d("test", "Berhasil")
            Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Log.d("test", it.toString())
            Toast.makeText(this, "Gagal disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}